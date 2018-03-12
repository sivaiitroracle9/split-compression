package org.agoda.compression;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class SplitFileWriter {
	protected int splitLength;
	private String filename;
	protected long byteswritten = 0;
	protected SplitCounter counter;
	private FileOutputStream fos;
	private boolean splitFileOpen = false;

	public SplitFileWriter(String filename, SplitCounter counter, int splitLength) {
		this.filename = filename;
		this.counter = counter;
		this.splitLength = splitLength;
	}

	protected boolean isSplitFileOpen() {
		return this.splitFileOpen;
	}

	protected void updateBytesWritten(int len) {
		this.byteswritten += len;
	}

	protected FileOutputStream getSplitFileOutputStream() {
		return this.fos;
	}
	
	protected int getSplitLength(){
		return this.splitLength;
	}

	protected File startNewSplitFile() throws IOException {
		if (isSplitFileOpen()) {
			closeSplitFile();
		}

		File newFile = new File(filename
				+ String.format("%010d", counter.getValue()));
		counter.inc();
		splitFileOpen = true;
		fos = new FileOutputStream(newFile);
		return newFile;
	}

	protected void closeSplitFile() throws IOException {
		if(isSplitFileOpen()) {
			fos.close();	
		}
		splitFileOpen = false;
	}
}
