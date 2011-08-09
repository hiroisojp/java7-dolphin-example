package com.hiroisojp.java7.forkjoin.mapreduce;

import java.util.List;
import java.util.concurrent.ForkJoinTask;

import com.hiroisojp.java7.forkjoin.mapreduce.Pair;

public class Reducer extends ForkJoinTask<Pair<Integer>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String key;

	private List<Integer> values;

	private Pair<Integer> result;

	public Reducer(String key, List<Integer> values) {
		this.key = key;
		this.values = values;
	}

	@Override
	public final Pair<Integer> getRawResult() {
		return result;
	}

	@Override
	protected final void setRawResult(Pair<Integer> value) {
		result = value;
	}

	@Override
	protected boolean exec() {
		// キーの単語に対して、バリューはその単語の使用回数のリスト
		// バリューの個々の値を足し合せて、合計を求める
		// 合計が全体での単語の使用回数になる
		int sum = 0;
		for (int value : values) {
			sum += value;
		}

		// 単語とその使用回数をペアにして返す
		result = new Pair<Integer>(key, sum);

		return true;
	}
}