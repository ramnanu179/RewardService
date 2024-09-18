package com.infosys.retailApp.services;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.infosys.retailApp.dto.response.CustomerRewards;
import com.infosys.retailApp.dto.response.MonthlyRewards;
import com.infosys.retailApp.entity.Customer;
import com.infosys.retailApp.entity.Transaction;
import com.infosys.retailApp.exception.CustomerNotFoundException;
import com.infosys.retailApp.repository.CustomerRepository;
import com.infosys.retailApp.repository.TransactionRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RewardServiceImpl implements RewardService{

	private CustomerRepository customerRepository;
	private TransactionRepository transactionRepository;

	public RewardServiceImpl(CustomerRepository customerRepository, TransactionRepository transactionRepository) {
		this.customerRepository = customerRepository;
		this.transactionRepository = transactionRepository;
	}

	
	/**
	 * @param custId
	 * @param months
	 * @return
	 */
	@Override
	public CustomerRewards getRewards(BigInteger custId, int months) {

		CustomerRewards customerRewards = new CustomerRewards();

		Customer customer = customerRepository.findById(custId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer is not available for given id '" + custId + "'. "));

		
		log.debug("Processing rewards for the customer  '{}'  ", customer.getName());

		LocalDateTime dateTime = LocalDateTime.now();
		LocalDateTime dateBeforeMonths = dateTime.minusMonths(months);
		List<Transaction> transactions = transactionRepository.findByCustomerIdAndDateAfter(custId, dateBeforeMonths);

		int currentYear = dateTime.getYear();
		if (Objects.nonNull(transactions) && !transactions.isEmpty()) {

			double totalRewardPoints = transactions.stream()
					.filter(transaction -> transaction.getDate().getYear() == currentYear)
					.mapToDouble(transaction -> calculateRewardPoints(transaction.getAmount())).sum();

			List<MonthlyRewards> monthlyRewards = transactions.stream()
					.filter(transaction -> transaction.getDate().getYear() == currentYear)
					.collect(Collectors.toMap(transaction -> String.valueOf(transaction.getDate().getMonth()),
							transaction -> calculateRewardPoints(transaction.getAmount()), this::mergeMonthRewards))
					.entrySet().stream().map(e -> {
						MonthlyRewards monthlyReward = new MonthlyRewards();
						monthlyReward.setMonth(e.getKey());
						monthlyReward.setPoints(e.getValue());
						return monthlyReward;
					}).collect(Collectors.toList());

			customerRewards.setName(customer.getName());
			customerRewards.setYear(currentYear);
			customerRewards.setTotalPoints(totalRewardPoints);
			customerRewards.setMonthWisePoints(monthlyRewards);
			
			
			log.debug("Rewards processed for the customer '{}'", customer.getName());

		}

		return customerRewards;

	}

	
	/**
	 * @param firstValue
	 * @param secondValue
	 * @return
	 */
	private double mergeMonthRewards(double firstValue, double secondValue) {
		return firstValue + secondValue;
	}
	
	/**
	 * @param transactionAmount
	 * @return
	 */
	private double calculateRewardPoints(BigDecimal transactionAmount) {
		double points = 0;
		double amount = transactionAmount.doubleValue();
		if (amount > 100) {
			points = points + (amount - 100) * 2;
			amount = 100;
		}
		if (amount > 50) {
			points = points + (amount - 50) * 1;
		}
		return points;
	}

}
