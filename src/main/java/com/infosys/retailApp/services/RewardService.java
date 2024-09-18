package com.infosys.retailApp.services;

import java.math.BigInteger;

import com.infosys.retailApp.dto.response.CustomerRewards;

public interface RewardService {
	
	public CustomerRewards getRewards(BigInteger custId, int months);

}
