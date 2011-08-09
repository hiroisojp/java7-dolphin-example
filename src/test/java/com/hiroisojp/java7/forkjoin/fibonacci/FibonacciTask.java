package com.hiroisojp.java7.forkjoin.fibonacci;

import java.util.concurrent.RecursiveTask;

public class FibonacciTask extends RecursiveTask<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final int n;

	public FibonacciTask(int n) {
		this.n = n;
	}

	@Override
	protected Integer compute() {
		if (n <= 1) {
			return n;
		}

		// n-1に対してフィボナッチ数を求める
		FibonacciTask f1 = new FibonacciTask(n - 1);
		f1.fork();

		// n-2に対してフィボナッチ数を求める
		FibonacciTask f2 = new FibonacciTask(n - 2);

		// 処理結果を足し合せて、nのフィボナッチ数とする
		return f2.compute() + f1.join();
	}
}