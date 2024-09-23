package com.infosys.retailApp.dto.response;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.Data;

@Data
public class TransactionRequest {
	private BigInteger customerId;
	private BigDecimal amount;
	
}
