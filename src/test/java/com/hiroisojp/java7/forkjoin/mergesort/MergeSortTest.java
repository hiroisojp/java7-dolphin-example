package com.hiroisojp.java7.forkjoin.mergesort;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.hiroisojp.java7.mergesort.MergeSortTask;

public class MergeSortTest {

	protected int dataSize = 10000;

	@Test
	public void testMergeSort() {
		Integer[] src = rangamIntegers();
		Integer[] dest = src.clone();

		ForkJoinPool pool = new ForkJoinPool();

		MergeSortTask task = new MergeSortTask(src, dest, 0, src.length);

		long start = System.nanoTime();
		pool.invoke(task);
		long end = System.nanoTime();

		long result = end - start;
		long mills = TimeUnit.MILLISECONDS
				.convert(result, TimeUnit.NANOSECONDS);
		System.out.println(mills + "ミリ秒");
	}

	protected Integer[] rangamIntegers() {
		Random random = new Random();
		Integer[] src = new Integer[dataSize];
		for (int i = 0; i < dataSize; i++) {
			src[i] = random.nextInt(dataSize);
		}
		return src;
	}

}
