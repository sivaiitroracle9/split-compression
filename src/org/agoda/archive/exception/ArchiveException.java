package org.agoda.archive.exception;

public class ArchiveException extends Exception {

	private static final long serialVersionUID = 1084462851365663306L;

	public ArchiveException() {
		super();
	}
	
	public ArchiveException(String message) {
		super(message);
	}
	
    public ArchiveException(String message, Throwable cause) {
        super(message, cause);
    }
}
