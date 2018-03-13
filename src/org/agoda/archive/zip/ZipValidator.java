package org.agoda.archive.zip;

import java.io.File;
import java.util.List;

import org.agoda.archive.CompressionUtility;
import org.agoda.archive.FileConstants;
import org.agoda.archive.exception.ArchiveException;
import org.agoda.archive.exception.UnArchiveException;

public class ZipValidator {

	public static void compress(String source, String destination, int max_size)
			throws ArchiveException {

		if (max_size < FileConstants.MIN_SPLIT_SIZE) {
			throw new ArchiveException(
					"max file size is less than MIN_SPILT_SIZE.");
		}

		File sfile = new File(source);
		if (sfile.exists())
			throw new ArchiveException(source + " file doest not exist.");

		if (!sfile.canRead())
			throw new ArchiveException(source + " file doest not exist.");

		List<File> sfiles = CompressionUtility.getSourceFiles(sfile);
		if (sfiles.isEmpty())
			throw new ArchiveException("No files to add into archive.");

		for (File f : sfiles) {
			if (f.canRead())
				throw new ArchiveException(f.getAbsolutePath()
						+ " file doest not exist.");
		}

		// TODO check if destination is file
		File dfile = new File(destination);
		if (dfile.exists())
			throw new ArchiveException(source + " file doest not exist.");

		if (!dfile.canWrite())
			throw new ArchiveException(source + " file doest not exist.");
	}

	public static void decompress(String source, String destination) throws UnArchiveException{

	}
}
