package org.agoda;

import org.agoda.archive.CompressionStrategy;
import org.agoda.archive.exception.ArchiveException;
import org.agoda.archive.exception.UnArchiveException;

public class ArchiveContext {

	private CompressionStrategy strategy;

	public void setCompressionStrategy(CompressionStrategy strategy) {
		this.strategy = strategy;
	}

	public void compress(String source, String destination, int max_size) throws IllegalAccessException, ArchiveException {
		strategy.compress(source, destination, max_size);
	}

	public void decompress(String source, String destination) throws IllegalAccessException, UnArchiveException {
		strategy.decompress(source, destination);
	}
}
