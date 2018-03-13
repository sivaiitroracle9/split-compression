package org.agoda.archive.exception;

public class UnArchiveException extends Exception {

	private static final long serialVersionUID = 1084462851365663306L;

	public UnArchiveException() {
		super();
	}
	
	public UnArchiveException(String message) {
		super(message);
	}
	
    public UnArchiveException(String message, Throwable cause) {
        super(message, cause);
    }
}
