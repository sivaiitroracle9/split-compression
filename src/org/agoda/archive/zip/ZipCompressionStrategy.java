package org.agoda.archive.zip;

import java.io.File;
import java.util.List;

import org.agoda.archive.CompressionStrategy;
import org.agoda.archive.CompressionUtility;
import org.agoda.archive.FileConstants;
import org.agoda.archive.exception.ArchiveException;
import org.agoda.archive.exception.UnArchiveException;
import org.agoda.partfile.SplitCounter;

public class ZipCompressionStrategy implements CompressionStrategy {

	@Override
	public void compress(String source, String destination, int max_size) throws ArchiveException {
		ZipValidator.compress(source, destination, max_size);
		
		File input = new File(source);
		List<File> iFiles = CompressionUtility.getSourceFiles(input);

		String zipname = source.substring(source
				.lastIndexOf(File.separator));

		String zipfileName = new File(destination, zipname + FileConstants.FILE_EXT_ZIP)
				.getAbsolutePath();

		ZipFileWriter zw = new ZipFileWriter(zipfileName, max_size,
				new SplitCounter());
		for (File f : iFiles) {
			String entry = f.getAbsolutePath().substring(
					source.length() + 1);
			zw.write(f, entry);
		}

	}

	@Override
	public void decompress(String source, String destination) throws UnArchiveException {
		ZipValidator.decompress(source, destination);
		File input = new File(source);
		List<File> iFiles = CompressionUtility.getSourceFiles(input);

		String fn = iFiles.get(0).getName();
		String decompressDir = iFiles.get(0).getName()
				.substring(0, fn.lastIndexOf(FileConstants.FILE_EXT_ZIP));
		File destDir = new File(destination, decompressDir);
		if (!destDir.exists())
			destDir.mkdir();
		ZipFileReader zr = new ZipFileReader();
		for (File f : iFiles) {
			zr.read(f, destDir.getAbsolutePath());
		}

	}
}
