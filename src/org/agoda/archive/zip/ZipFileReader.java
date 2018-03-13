package org.agoda.archive.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.agoda.archive.FileConstants;
import org.agoda.archive.partfile.PartFileMerger;

public class ZipFileReader extends PartFileMerger {

	private BufferedInputStream bis;

	public void read(File file, String destination){
		ZipFile zf;
		try {
			zf = new ZipFile(file);
		
			Enumeration<? extends ZipEntry> e = zf.entries();
			List<? extends ZipEntry> list = Collections.list(e);
			Collections.sort(list, new SplitFileComparator());
			for (ZipEntry entry : list) {

				if (entry.isDirectory()) {
					continue;
				} else {
					System.out.println("Extracting file: " + entry.getName());
					File outFile = new File(destination, entry.getName());

					bis = new BufferedInputStream(zf.getInputStream(entry));

					write(bis, outFile);

					bis.close();

				}

			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if(bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	private class SplitFileComparator implements Comparator<ZipEntry> {

		@Override
		public int compare(ZipEntry ze1, ZipEntry ze2) {
			String s1 = ze1.getName();
			String s2 = ze2.getName();
			int i1 = Integer.parseInt(s1.substring(s1
					.lastIndexOf(FileConstants.FILE_EXT_ZIP)
					+ FileConstants.FILE_EXT_ZIP.length()));
			int i2 = Integer.parseInt(s2.substring(s2
					.lastIndexOf(FileConstants.FILE_EXT_ZIP)
					+ FileConstants.FILE_EXT_ZIP.length()));
			return i1 - i2;
		}

	}
}
