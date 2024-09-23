package com.infosys.retailApp.exception;

public class TransactionRequestNotValidException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	String message;

	public TransactionRequestNotValidException(String message) {
		super(message);
	}
}
