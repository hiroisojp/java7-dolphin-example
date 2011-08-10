package com.hiroisojp.java7.forkjoin.mergesort;

import java.util.concurrent.RecursiveAction;

public class MergeSortTask extends RecursiveAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static int INSERTIONSORT_THRESHOLD = 7;

	final Object[] src;

	final Object[] dest;

	int low;

	int high;

	public MergeSortTask(Object[] src, Object[] dest, int low, int high) {
		this.src = src;
		this.dest = dest;
		this.low = low;
		this.high = high;
	}

	private static void swap(Object[] x, int a, int b) {
		Object t = x[a];
		x[a] = x[b];
		x[b] = t;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void compute() {
		int length = high - low;

		if (length <= INSERTIONSORT_THRESHOLD) {
			for (int i = low; i < high; i++) {
				for (int j = i; j > low
						&& ((Comparable) dest[j - 1]).compareTo(dest[j]) > 0; j--) {
					swap(dest, j, j - 1);
				}
			}
			return;
		}

		int destLow = low;
		int destHigh = high;
		int mid = (low + high) >>> 1;

		MergeSortTask left = new MergeSortTask(dest, src, low, mid);
		left.fork();

		MergeSortTask right = new MergeSortTask(dest, src, mid, high);
		right.compute();
		left.join();

		if (((Comparable) src[mid - 1]).compareTo(src[mid]) <= 0) {
			System.arraycopy(src, low, dest, destLow, length);
			return;
		}

		for (int i = destLow, p = low, q = mid; i < destHigh; i++) {
			if (q >= high || p < mid
					&& ((Comparable) src[p]).compareTo(src[q]) <= 0) {
				dest[i] = src[p++];
			}
			else {
				dest[i] = src[q++];
			}
		}
	}

}