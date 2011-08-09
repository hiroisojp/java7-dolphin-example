package com.hiroisojp.java7.forkjoin.mapreduce;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ForkJoinPool;

import org.junit.Test;

import com.hiroisojp.java7.forkjoin.mapreduce.Mapper;

public class MapReduceTest {

	String path = "src/test/resources/sample_forkjoin_mapreduce.txt";

	@Test
	public void testMapReduce() {
		List<String> inputs = createInputs(path);

		ForkJoinPool pool = new ForkJoinPool();

		// map
		List<Mapper> mappers = new ArrayList<>();
		map(pool, mappers, inputs);

		// shuffle
		Map<String, List<Integer>> midResults = shuffle(mappers);

		// reduce
		List<Reducer> reducers = reduce(pool, midResults);

		for (Reducer reducer : reducers) {
			Pair<Integer> pair = reducer.join();
			System.out.println(pair.getKey() + " " + pair.getValue());
		}
	}

	protected List<String> createInputs(String path) {
		List<String> inputs = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String content;
			while ((content = reader.readLine()) != null) {
				inputs.add(content);
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return inputs;
	}

	protected void map(ForkJoinPool pool, List<Mapper> mappers,
			List<String> inputs) {

		// Map処理で行に含まれる単語をカウント
		for (String input : inputs) {
			Mapper mapper = new Mapper(input);
			pool.invoke(mapper);
			mappers.add(mapper);
		}
	}

	protected Map<String, List<Integer>> shuffle(List<Mapper> mappers) {
		// Map処理の結果をシャッフル
		Map<String, List<Integer>> midResults = new TreeMap<>();
		for (Mapper mapper : mappers) {
			List<Pair<Integer>> results = mapper.join();
			for (Pair<Integer> pair : results) {
				List<Integer> list = midResults.get(pair.getKey());
				if (list == null) {
					list = new ArrayList<>();
					midResults.put(pair.getKey(), list);
				}
				list.add(pair.getValue());
			}
		}
		return midResults;
	}

	protected List<Reducer> reduce(ForkJoinPool pool,
			Map<String, List<Integer>> midResults) {
		// Reduce処理で単語ごとの使用回数をカウント
		List<Reducer> reducers = new ArrayList<>();
		for (String key : midResults.keySet()) {
			Reducer reducer = new Reducer(key, midResults.get(key));
			pool.invoke(reducer);
			reducers.add(reducer);
		}
		return reducers;
	}

}