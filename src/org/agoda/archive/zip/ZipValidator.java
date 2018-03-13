package org.agoda.archive.zip;

import org.agoda.archive.ArchiveException;
import org.agoda.archive.FileConstants;

public class ZipValidator {

	public void compress(String source, String destination, int max_size) throws ArchiveException {
		
		if(max_size < FileConstants.MIN_SPLIT_SIZE) {
			throw new ArchiveException("max file size is less than MIN_SPILT_SIZE.");
		}
		
		// Readability exceptions
		// file, folder existence
		
		// writability
		// file folder existence
	}
}
