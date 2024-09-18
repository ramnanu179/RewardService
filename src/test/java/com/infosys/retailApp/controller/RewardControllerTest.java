package com.infosys.retailApp.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.infosys.retailApp.dto.response.CustomerRewards;
import com.infosys.retailApp.exception.CustomerNotFoundException;
import com.infosys.retailApp.services.RewardService;

@WebMvcTest(RewardController.class)
public class RewardControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RewardService rewardService;

	@Test
	void testCustomerNotFoundFailure() throws Exception {
		BigInteger custId = new BigInteger("1");

		when(rewardService.getRewards(eq(custId), anyInt()))
				.thenThrow(new CustomerNotFoundException("customer not found"));
		mockMvc.perform(MockMvcRequestBuilders.get("/api/1.1/reward/customer/{custId}", custId).param("months", "3"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testMonthNotValidFailure() throws Exception {
		BigInteger custId = new BigInteger("1");

		mockMvc.perform(MockMvcRequestBuilders.get("/api/1.1/reward/customer/{custId}", custId).param("months", "0"))
				.andExpect(status().isBadRequest());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/1.1/reward/customer/{custId}", custId).param("months", "14"))
				.andExpect(status().isBadRequest());

	}

	@Test
	void testGetRewards() throws Exception {
		BigInteger custId = new BigInteger("1");

		CustomerRewards customerRewards = new CustomerRewards();
		customerRewards.setYear(LocalDateTime.now().getYear());
		customerRewards.setTotalPoints(350);
		when(rewardService.getRewards(eq(custId), anyInt())).thenReturn(customerRewards);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/1.1/reward/customer/{custId}", custId).param("months", "3"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json("{\"totalPoints\":350}"));
	}

}
