package com.infosys.retailApp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.infosys.retailApp.dto.response.CustomerRewards;
import com.infosys.retailApp.entity.Customer;
import com.infosys.retailApp.entity.Transaction;
import com.infosys.retailApp.exception.CustomerNotFoundException;
import com.infosys.retailApp.repository.CustomerRepository;
import com.infosys.retailApp.repository.TransactionRepository;

public class RewardServiceTest {
	
	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private RewardServiceImpl rewardServiceImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetCustomerRewardsInvalidCustomerId() {
		BigInteger custId = new BigInteger("2");
		when(customerRepository.findById(custId)).thenReturn(Optional.ofNullable(null));

		CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
				() -> rewardServiceImpl.getRewards(custId, 12));
		assertNotNull(exception);
	}

	@Test
	void testGetRewards() {
		BigInteger custId = new BigInteger("1");
		Customer customer = new Customer();

		customer.setName("Rajesh");
		customer.setId(custId);
		customer.setEmailId("rajesh1@gmail.com");
		customer.setPhNumber("1232424");

		Transaction transaction = new Transaction();
		transaction.setId(UUID.randomUUID());
		transaction.setCustomer(customer);
		transaction.setAmount(new BigDecimal("180"));
		transaction.setDate(LocalDateTime.now().minusMonths(1));

		Transaction transaction2 = new Transaction();
		transaction2.setId(UUID.randomUUID());
		transaction2.setCustomer(customer);
		transaction2.setAmount(new BigDecimal("50"));
		transaction2.setDate(LocalDateTime.now().minusMonths(2));

		Transaction transaction3 = new Transaction();
		transaction3.setId(UUID.randomUUID());
		transaction3.setCustomer(customer);
		transaction3.setAmount(new BigDecimal("60"));
		transaction3.setDate(LocalDateTime.now().minusMonths(5));

		when(customerRepository.findById(custId)).thenReturn(Optional.of(customer));
		when(transactionRepository.findByCustomerIdAndDateAfter(eq(custId), any(LocalDateTime.class)))
				.thenReturn(Arrays.asList(transaction, transaction2, transaction3));

		CustomerRewards customerRewards = rewardServiceImpl.getRewards(custId, 6);

		assertNotNull(customerRewards);
		assertEquals(220, customerRewards.getTotalPoints());
		assertEquals(3, customerRewards.getMonthWisePoints().size());
	}

}
