package com.osrmt.www;

public class NotLoggedInException extends Exception {

	public NotLoggedInException() {
		super();
	}

	public NotLoggedInException(String message) {
		super(message);
	}

	public NotLoggedInException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotLoggedInException(Throwable cause) {
		super(cause);
	}

}

