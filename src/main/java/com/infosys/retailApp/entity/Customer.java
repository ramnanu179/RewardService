package com.infosys.retailApp.entity;

import java.math.BigInteger;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Data
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private BigInteger id;

	private String name;
	@Email(message = "Email id must be in valid format")
	private String emailId;
	private String phNumber;

}
