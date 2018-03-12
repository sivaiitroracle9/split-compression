package org.agoda.archive;


public interface CompressionStrategy {

	public void compress(String source, String destination, int max_size);

	public void decompress(String source, String destination);
	
}
