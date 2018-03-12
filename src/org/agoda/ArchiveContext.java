package org.agoda;

import org.agoda.archive.CompressionStrategy;

public class ArchiveContext {

	private CompressionStrategy strategy;

	public void setCompressionStrategy(CompressionStrategy strategy) {
		this.strategy = strategy;
	}

	public void compress(String source, String destination, int max_size) throws IllegalAccessException {
		strategy.compress(source, destination, max_size);
	}

	public void decompress(String source, String destination) throws IllegalAccessException {
		strategy.decompress(source, destination);
	}
}
