package com.hiroisojp.java7.forkjoin.fibonacci;

import static junit.framework.Assert.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import org.junit.Test;

public class FibonacciTest {

	@Test
	public void testFibonacci() throws InterruptedException, ExecutionException {
		ForkJoinPool pool = new ForkJoinPool();
		// 処理が終るまで待ち、処理結果を受け取る
		int result = pool.invoke(new FibonacciTask(10));
		assertEquals(55, result);

		// 非同期実行を行なう。処理結果は受け取らない
		pool.execute(new FibonacciTask(10));

		// 非同期実行を行ない、処理結果はタスクから受け取る
		ForkJoinTask<Integer> task = new FibonacciTask(10);
		pool.submit(task);
		// 非同期なのでタイミングによっては、一致しない
		System.out.println(task.get());

	}

}
