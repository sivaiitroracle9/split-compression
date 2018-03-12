package org.agoda.archive.zip;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

import org.agoda.archive.CompressionStrategy;
import org.agoda.archive.CompressionUtility;
import org.agoda.archive.partfile.SplitCounter;

public class ZipCompressionStrategy implements CompressionStrategy {

	@Override
	public void compress(String source, String destination, int max_size) {

		File input = new File(source);
		List<File> iFiles = CompressionUtility.getSourceFiles(input);

		String zipname = source.substring(source
				.lastIndexOf(File.separator));

		String zipfileName = new File(destination, zipname + ".zip")
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
	public void decompress(String source, String destination) {
		File input = new File(source);
		List<File> iFiles = CompressionUtility.getSourceFiles(input);

		String fn = iFiles.get(0).getName();
		String decompressDir = iFiles.get(0).getName()
				.substring(0, fn.lastIndexOf(".zip"));
		File destDir = new File(destination, decompressDir);
		if (!destDir.exists())
			destDir.mkdir();
		destination = destDir.getAbsolutePath();
		ZipFileReader zr = new ZipFileReader();
		try {
			for (File f : iFiles) {
				zr.read(f, destination);
			}
		} catch (IOException | DataFormatException e) {
			e.printStackTrace();
		}

	}
}
