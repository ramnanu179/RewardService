package com.infosys.retailApp.exception;

public class CustomerNotFoundException extends RuntimeException{

	  private static final long serialVersionUID = -3859609338678148904L;

	    String message;
		public CustomerNotFoundException(String message) {
	        super(message);
	        this.message = message;
	    }
}
