package org.agoda.compression;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

public class ZipCompressionStrategy implements CompressionStrategy {

	@Override
	public void compress(String source, String destination, int max_size) {

		try {

			File input = new File(source);
			List<File> iFiles = CompressionUtility.getSourceFiles(input);

			String zipname = source.substring(source
					.lastIndexOf(File.separator));

			String zipfileName = new File(destination, zipname + ".zip")
					.getAbsolutePath();

			SplitCounter counter = new SplitCounter();

			for (File f : iFiles) {
				String entry = f.getAbsolutePath().substring(
						source.length() + 1);
				ZipFileWriter zw = new ZipFileWriter(f, zipfileName, max_size,
						entry, counter);
				zw.write();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void decompress(String source, String destination) {
		File input = new File(source);
		List<File> iFiles = CompressionUtility.getSourceFiles(input);
		
		String fn = iFiles.get(0).getName();
		String decompressDir = iFiles.get(0).getName().substring(0, fn.lastIndexOf(".zip"));
		File destDir = new File(destination, decompressDir);
		if(!destDir.exists())
			destDir.mkdir();
		destination = destDir.getAbsolutePath(); 
		
		try {
			for(File f : iFiles) {
				ZipFileReader zr = new ZipFileReader();
				zr.read(f, destination);
			}
		} catch (IOException | DataFormatException e) {
			e.printStackTrace();
		}

	}
}
