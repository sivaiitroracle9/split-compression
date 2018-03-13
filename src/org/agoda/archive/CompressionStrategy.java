package org.agoda.archive;

import org.agoda.archive.exception.ArchiveException;
import org.agoda.archive.exception.UnArchiveException;


public interface CompressionStrategy {

	public void compress(String source, String destination, int max_size) throws ArchiveException;

	public void decompress(String source, String destination) throws UnArchiveException;
	
}
