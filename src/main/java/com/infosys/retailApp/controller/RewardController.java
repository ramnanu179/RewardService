package com.infosys.retailApp.controller;

import java.math.BigInteger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.retailApp.constants.RewardsServiceConstants;
import com.infosys.retailApp.dto.response.CustomerRewards;
import com.infosys.retailApp.dto.response.ErrorResponse;
import com.infosys.retailApp.exception.CustomerNotFoundException;
import com.infosys.retailApp.exception.RewardServiceMonthInvalidException;
import com.infosys.retailApp.services.RewardService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("api/1.1/reward")
@Log4j2
public class RewardController {

	private RewardService rewardService;

	private RewardController(RewardService rewardService) {
		this.rewardService = rewardService;

	}

	@GetMapping("customer/{custId}")
	public ResponseEntity<CustomerRewards> getRewards(@PathVariable BigInteger custId,
			@RequestParam(value = "months", defaultValue = RewardsServiceConstants.DEFAULT_MONTHS) int months) {

		if (months < 1 || months > 12) {
			log.error("Month value should be 1 to 12 inclusive. Invalid value {} ", months);
			throw new RewardServiceMonthInvalidException("Month value should be 1 to 12 inclusive.");
		}
		return ResponseEntity.ok(rewardService.getRewards(custId, months));
	}

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
	
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleException(Exception ex) {
		return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}
}
