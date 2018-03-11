package org.agoda.compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileWriter {
	private int splitLength;
	private String zipfilename;
	private String entry;
	private File sourceFile;
	private long byteswritten = 0;
	private SplitCounter counter;
	private FileOutputStream fos;
	private ZipOutputStream zos;
	private ZipEntry ze;
	private boolean entryOpened = false;
	private Deflater compressor = new Deflater();
	byte[] outbuffer = new byte[CompressionConstants.BUFF_SIZE];

	public ZipFileWriter(File sourceFile, String zipFilename, int splitLength,
			String entry, SplitCounter counter) {
		this.sourceFile = sourceFile;
		this.zipfilename = zipFilename;
		this.splitLength = splitLength;
		this.entry = entry;
		this.counter = counter;
	}

	public void write() throws IOException {

		FileInputStream fis = new FileInputStream(sourceFile);
		int len = 0;
		byte[] inbuffer = new byte[CompressionConstants.BUFF_SIZE];
		while ((len = fis.read(inbuffer)) > 0) {
			write(inbuffer, len);
		}
		if (entryOpened) {
			close();
		}
		compressor.end();
	}

	public void write(byte[] buff, int len) throws IOException {
		int clen = compressLength(buff, len);

		if (!entryOpened) {
			open();
		} else if (byteswritten + clen > splitLength) {
			close();
			open();
			byteswritten = 0;
		}
		
		zos.write(buff, 0, len);
		zos.flush();
		byteswritten += clen;
	}

	private void open() throws IOException {
		fos = new FileOutputStream(newSplitFile());
		zos = new ZipOutputStream(fos);
		ze = new ZipEntry(entry);
		zos.putNextEntry(ze);
		entryOpened = true;
	}

	private void close() throws IOException {
		entryOpened = false;
		zos.closeEntry();
		zos.close();
		fos.close();
	}

	private int compressLength(byte[] buff, int len) {
		compressor.setInput(buff, 0, len);
		compressor.finish();
		int clen = compressor.deflate(outbuffer);
		compressor.reset();
		return clen;
	}

	private File newSplitFile() {
		File newFile = new File(zipfilename
				+ String.format("%010d", counter.getValue()));
		counter.inc();
		return newFile;
	}
}
