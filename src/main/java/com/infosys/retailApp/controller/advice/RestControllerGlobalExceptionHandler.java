package com.infosys.retailApp.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.infosys.retailApp.dto.response.ErrorResponse;
import com.infosys.retailApp.exception.CustomerNotFoundException;
import com.infosys.retailApp.exception.RewardServiceMonthInvalidException;
import com.infosys.retailApp.exception.TransactionRequestNotValidException;

@RestControllerAdvice
public class RestControllerGlobalExceptionHandler {

	@ExceptionHandler(value = RewardServiceMonthInvalidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleRewardServiceMonthInvalidException(RewardServiceMonthInvalidException ex) {
		return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}

	@ExceptionHandler(value = CustomerNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleCustomerNotFoundException(CustomerNotFoundException ex) {
		return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}

	@ExceptionHandler(value = RuntimeException.class)
	public ErrorResponse handleException(RuntimeException ex) {
		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
	}

	@ExceptionHandler(value = Exception.class)
	public ErrorResponse handleException(Exception ex) {
		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
	}

	@ExceptionHandler(value = TransactionRequestNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleTransactionRequestNotValidException(TransactionRequestNotValidException ex) {
		return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}
}
