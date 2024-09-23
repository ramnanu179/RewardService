package com.infosys.retailApp.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.infosys.retailApp.dto.response.TransactionRequest;
import com.infosys.retailApp.services.TransactionService;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionService transactionService;

	@Test
	void testTransactionRequestNotValidFailure() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/api/1.1/transaction/create")
				.contentType(MediaType.APPLICATION_JSON).content("{\"amount\":100}"))
				.andExpect(status().isBadRequest());

		mockMvc.perform(MockMvcRequestBuilders.post("/api/1.1/transaction/create")
				.contentType(MediaType.APPLICATION_JSON).content("{\"customerId\":\"" + new BigInteger("1") + "\"}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testTransactionRequestSuccess() throws Exception {

		BigInteger customerId = new BigInteger("1");
		TransactionRequest transactionRequest = new TransactionRequest();
		transactionRequest.setCustomerId(customerId);
		transactionRequest.setAmount(new BigDecimal("120"));
		doNothing().when(transactionService).saveTransaction(transactionRequest);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/1.1/transaction/create").contentType(MediaType.APPLICATION_JSON)
						.content("{\"customerId\":\"" + customerId + "\", \"amount\":120}"))
				.andExpect(status().isOk()).andExpect(content().string("Transaction saved Successfully"));
	}

}
