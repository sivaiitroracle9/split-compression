package org.agoda.compression;

public class CompressionContext {

	private CompressionStrategy strategy;

	public void setCompressionStrategy(CompressionStrategy strategy) {
		this.strategy = strategy;
	}

	public void compress(String source, String destination, int max_size) throws IllegalAccessException {
		// Validator.compression(source, destination, max_size);
		strategy.compress(source, destination, max_size);
	}

	public void decompress(String source, String destination) throws IllegalAccessException {
		// Validator.decompression();
		strategy.decompress(source, destination);
	}
}
