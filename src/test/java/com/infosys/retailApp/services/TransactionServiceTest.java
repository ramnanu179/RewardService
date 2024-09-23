package com.infosys.retailApp.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.infosys.retailApp.dto.response.TransactionRequest;
import com.infosys.retailApp.entity.Customer;
import com.infosys.retailApp.entity.Transaction;
import com.infosys.retailApp.exception.CustomerNotFoundException;
import com.infosys.retailApp.repository.CustomerRepository;
import com.infosys.retailApp.repository.TransactionRepository;

public class TransactionServiceTest {
	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private TransactionServiceImpl transactionServiceImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSaveTransactionForInvalidCustomerId() {
		BigInteger customerId = new BigInteger("1");
		TransactionRequest transactionRequest = new TransactionRequest();
		transactionRequest.setCustomerId(customerId);
		transactionRequest.setAmount(new BigDecimal("130"));

		when(customerRepository.findById(customerId)).thenReturn(Optional.ofNullable(null));

		CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
				() -> transactionServiceImpl.saveTransaction(transactionRequest));
		assertNotNull(exception);

	}

	@Test
	void testSaveTransactionForCustomerId() {

		BigInteger customerId = new BigInteger("1");
		TransactionRequest transactionRequest = new TransactionRequest();
		transactionRequest.setCustomerId(customerId);
		transactionRequest.setAmount(new BigDecimal("130"));

		Customer customer = new Customer();

		customer.setName("Rajesh");
		customer.setId(customerId);
		customer.setEmailId("rajesh1@gmail.com");
		customer.setPhNumber("1232424");
		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

		transactionServiceImpl.saveTransaction(transactionRequest);
		verify(transactionRepository, times(1)).save(any(Transaction.class));

	}

}
