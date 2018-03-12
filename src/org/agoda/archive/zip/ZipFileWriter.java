package org.agoda.archive.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.agoda.archive.partfile.PartFileWriter;
import org.agoda.archive.partfile.SplitCounter;

public class ZipFileWriter extends PartFileWriter {
	private ZipOutputStream zos;
	private Deflater compressor = new Deflater();
	boolean isZipOSOpen = false;
	private int BUFF_SIZE = 8*1024;
	byte[] outbuffer;

	public ZipFileWriter(String zipFilename, int splitLength,
			SplitCounter counter) {
		super(zipFilename, counter, splitLength);
		outbuffer = new byte[BUFF_SIZE];
	}

	public void write(File sourceFile, String entry) {
		FileInputStream fis = null;
		compressor = new Deflater();
		try {
			createCompressedFile();
			putEntry(entry);

			fis = new FileInputStream(sourceFile);
			byte[] inbuffer = new byte[BUFF_SIZE];
			int len = 0;
			while ((len = fis.read(inbuffer)) > 0) {
				write(inbuffer, len, entry);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (fis != null)
					fis.close();

				closeEntry();

				closeCompressedFile();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		compressor.end();
	}

	public void write(byte[] buffer, int len, String entry) throws IOException {
		int clen = compressLength(buffer, len);
		if (byteswritten + clen > getSplitLength()) {
			createCompressedFile();
			putEntry(entry);
		}

		zos.write(buffer, 0, len);
		updateBytesWritten(clen);
	}

	private int compressLength(byte[] buff, int len) {
		compressor.setInput(buff, 0, len);
		compressor.finish();
		int clen = compressor.deflate(outbuffer);
		compressor.reset();
		return clen;
	}

	public void createCompressedFile() throws IOException {

		if (isZipOSOpen) {
			closeCompressedFile();
		}
		startNewSplitFile();
		zos = new ZipOutputStream(getSplitFileOutputStream());
		isZipOSOpen = true;
	}

	public void closeCompressedFile() throws IOException {
		if (isZipOSOpen) {
			zos.close();
		}
		isZipOSOpen = false;
		closeSplitFile();
	}

	public void putEntry(String entry) throws IOException {
		zos.putNextEntry(new ZipEntry(entry));
	}

	public void closeEntry() throws IOException {
		zos.closeEntry();
	}

}
