package org.agoda.compression;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CompressionUtility {
	private static List<File> sourceFiles;
		
	public static boolean canRead(File f) {
		return f.canRead();
	}
	
	public static boolean canWrite(File f) {
		return f.canWrite();
	}

	public static boolean isDirectory(File file) {
		if (file == null) {
			throw new IllegalArgumentException("null file.");
		}
		return file.isDirectory();
	}

	public static List<File> getSourceFiles(File file) {
		if (file == null) {
			throw new IllegalArgumentException("null file.");
		}
		sourceFiles = new ArrayList<File>();
		if (file.isFile())
			sourceFiles.add(file);
		else
			listFiles(sourceFiles, file);

		return sourceFiles;
	}

	private static void listFiles(List<File> source, File file) {
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isFile())
				source.add(f);
			else
				listFiles(source, f);
		}
	}
}
