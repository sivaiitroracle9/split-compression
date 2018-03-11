package org.agoda.compression;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ZipFileReader {
	private Inflater decompressor = new Inflater();
	byte[] outbuffer = new byte[8 * 1024];

	public void read(File file, String destination) throws ZipException,
			IOException, DataFormatException {
		ZipFile zf = new ZipFile(file);
		Enumeration<? extends ZipEntry> e = zf.entries();
		while (e.hasMoreElements()) {
			ZipEntry entry = e.nextElement();
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
//					decompressor.setInput(inbuffer, 0, b);
//					int dlen = decompressor.inflate(outbuffer);
//					bos.write(outbuffer, 0, dlen);
					
					bos.write(inbuffer, 0, b);
					bos.flush();
				}

				bis.close();
				bos.close();
				fos.close();
			}
		}
	}
}
