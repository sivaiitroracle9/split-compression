package org.agoda.compression;

import java.io.IOException;
import java.util.zip.ZipException;

public interface CompressionStrategy {

	public void compress(String source, String destination, int max_size);

	public void decompress(String source, String destination);
	
}
