package com.infosys.retailApp.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class CustomerRewards {
	private String name;
	private Integer year;
	private double totalPoints;
	private List<MonthlyRewards> monthWisePoints;
}
