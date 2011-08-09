package com.hiroisojp.java7.forkjoin.mapreduce;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ForkJoinTask;

public class Mapper extends ForkJoinTask<List<Pair<Integer>>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String value;

	private List<Pair<Integer>> result;

	public Mapper(String value) {
		this.value = value;
	}

	@Override
	public final List<Pair<Integer>> getRawResult() {
		return result;
	}

	@Override
	protected final void setRawResult(List<Pair<Integer>> value) {
		result = value;
	}

	@Override
	protected boolean exec() {
		StringTokenizer tokenizer = new StringTokenizer(value);
		result = new ArrayList<>();

		while (tokenizer.hasMoreTokens()) {
			result.add(new Pair<Integer>(tokenizer.nextToken(), 1));
		}

		return true;
	}
}