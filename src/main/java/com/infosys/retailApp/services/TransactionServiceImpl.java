package com.infosys.retailApp.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.infosys.retailApp.dto.response.TransactionRequest;
import com.infosys.retailApp.entity.Customer;
import com.infosys.retailApp.entity.Transaction;
import com.infosys.retailApp.exception.CustomerNotFoundException;
import com.infosys.retailApp.repository.CustomerRepository;
import com.infosys.retailApp.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {
	private TransactionRepository transactionRepository;
	private CustomerRepository customerRepository;

	public TransactionServiceImpl(TransactionRepository transactionRepository, CustomerRepository customerRepository) {
		this.transactionRepository = transactionRepository;
		this.customerRepository = customerRepository;

	}

	@Transactional
	@Override
	public void saveTransaction(TransactionRequest transactionRequest) {

		Customer customer = customerRepository.findById(transactionRequest.getCustomerId())
				.orElseThrow(() -> new CustomerNotFoundException(
						"Customer is not available for given id '" + transactionRequest.getCustomerId() + "'. "));

		transactionRepository.save(populateTransactionEntity(transactionRequest, customer));
	}

	private Transaction populateTransactionEntity(TransactionRequest transactionRequest, Customer customer) {
		Transaction transaction = new Transaction();

		transaction.setAmount(transactionRequest.getAmount());
		transaction.setCustomer(customer);
		transaction.setDate(LocalDateTime.now());
		return transaction;
	}

}
