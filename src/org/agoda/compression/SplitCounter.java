package org.agoda.compression;

public class SplitCounter {
	private int value = 0;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int inc() {
		value++;
		return value;
	}
}
