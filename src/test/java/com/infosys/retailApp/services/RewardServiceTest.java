package com.infosys.retailApp.services;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.infosys.retailApp.repository.CustomerRepository;
import com.infosys.retailApp.repository.TransactionRepository;

public class RewardServiceTest {
	@InjectMocks
    private RewardService rewardService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}
