package com.infosys.retailApp.controller;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.retailApp.dto.response.TransactionRequest;
import com.infosys.retailApp.exception.TransactionRequestNotValidException;
import com.infosys.retailApp.services.TransactionService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("api/1.1/transaction")
@Log4j2
public class TransactionController {
	private TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/create")
	public ResponseEntity<String> createTransaction(@RequestBody TransactionRequest transactionRequest) {
		if (Objects.isNull(transactionRequest) || Objects.isNull(transactionRequest.getAmount())
				|| Objects.isNull(transactionRequest.getCustomerId())) {
			log.error("Invalid transaction request : {}", transactionRequest);
			throw new TransactionRequestNotValidException(
					"Transaction Request is not valid please  correct and retry.");
		}
		transactionService.saveTransaction(transactionRequest);

		return ResponseEntity.ok("Transaction saved Successfully");

	}

}