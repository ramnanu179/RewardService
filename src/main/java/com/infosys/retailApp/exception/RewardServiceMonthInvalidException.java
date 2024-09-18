package com.infosys.retailApp.exception;

public class RewardServiceMonthInvalidException extends RuntimeException {
	
    private static final long serialVersionUID = -3859609338678148904L;

    String message;
	public RewardServiceMonthInvalidException(String message) {
        super(message);
        this.message = message;
    }
}
