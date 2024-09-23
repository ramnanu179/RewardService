
package com.infosys.retailApp.services;

import com.infosys.retailApp.dto.response.TransactionRequest;

public interface TransactionService {
	public void saveTransaction(TransactionRequest transactionRequest);
}
