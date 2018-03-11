package org.agoda.compression;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ZipFileReader {

	public void read(File file, String destination) throws ZipException,
			IOException, DataFormatException {
		ZipFile zf = new ZipFile(file);
		Enumeration<? extends ZipEntry> e = zf.entries();
		List<? extends ZipEntry> list = Collections.list(e);
		Collections.sort(list, new SplitFileComparator());
		for (ZipEntry entry : list) {

			if (entry.isDirectory()) {
				continue;
			} else {
				System.out.println("Extracting file: " + entry.getName());
				File outFile = new File(destination, entry.getName());
				outFile.getParentFile().mkdirs();

				FileOutputStream fos;
				if (outFile.exists())
					fos = new FileOutputStream(outFile, true);
				else
					fos = new FileOutputStream(outFile);
				int b;
				byte[] inbuffer = new byte[8 * 1024];
				BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
				BufferedInputStream bis = new BufferedInputStream(
						zf.getInputStream(entry));

				while ((b = bis.read(inbuffer)) != -1) {
					bos.write(inbuffer, 0, b);
					bos.flush();
				}

				bis.close();
				bos.close();
				fos.close();
			}

		}
	}

	private class SplitFileComparator implements Comparator<ZipEntry> {

		@Override
		public int compare(ZipEntry ze1, ZipEntry ze2) {
			String s1 = ze1.getName();
			String s2 = ze2.getName();
			int i1 = Integer
					.parseInt(s1.substring(s1.lastIndexOf('.') + 4));
			int i2 = Integer
					.parseInt(s2.substring(s2.lastIndexOf('.') + 4));
			return i1 - i2;
		}

	}
}
