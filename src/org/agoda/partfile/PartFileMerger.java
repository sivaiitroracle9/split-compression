package org.agoda.partfile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class PartFileMerger {

	private FileOutputStream fos;
	private BufferedOutputStream bos;
	private int BUFF_SIZE = 8192;

	public void createDirectories(File outfile) {
		outfile.getParentFile().mkdirs();
	}

	public void write(BufferedInputStream is, File outfile) throws IOException {
		createDirectories(outfile);
		bos = new BufferedOutputStream(
				createPartFileOutputStream(outfile, true));
		int b;
		byte[] inbuffer = new byte[BUFF_SIZE];
		while ((b = is.read(inbuffer)) != -1) {
			bos.write(inbuffer, 0, b);
			bos.flush();
		}
		closePartFileOutputStream();
	}

	private FileOutputStream createPartFileOutputStream(File outfile,
			boolean append) throws FileNotFoundException {
		if (outfile.exists())
			fos = new FileOutputStream(outfile, true);
		else
			fos = new FileOutputStream(outfile);
		return this.fos;
	}

	public void closePartFileOutputStream() {
		if (bos != null)
			try {
				bos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		if (fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
