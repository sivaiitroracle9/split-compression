package org.agoda.compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileWriter extends SplitFileWriter {
	private ZipOutputStream zos;
	private Deflater compressor = new Deflater();
	byte[] outbuffer = new byte[CompressionConstants.BUFF_SIZE];
	boolean isZipOSOpen = false;

	public ZipFileWriter(String zipFilename, int splitLength,
			SplitCounter counter) {
		super(zipFilename, counter, splitLength);

		this.compressor = new Deflater();
	}

	public void write(File sourceFile, String entry) {
		FileInputStream fis = null;
		try {
			createCompressedFile();
			putEntry(entry);
			
			fis = new FileInputStream(sourceFile);
			byte[] inbuffer = new byte[CompressionConstants.BUFF_SIZE];
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
			startNewSplitFile();
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
		closeSplitFile();
	}

	public void putEntry(String entry) throws IOException {
		zos.putNextEntry(new ZipEntry(entry));
	}

	public void closeEntry() throws IOException {
		zos.closeEntry();
	}

}
