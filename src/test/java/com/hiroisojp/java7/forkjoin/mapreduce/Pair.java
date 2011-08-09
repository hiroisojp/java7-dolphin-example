package com.hiroisojp.java7.forkjoin.mapreduce;

public class Pair<T> {
	private String key;

	private T value;

	public Pair(String key, T value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public T getValue() {
		return value;
	}
}