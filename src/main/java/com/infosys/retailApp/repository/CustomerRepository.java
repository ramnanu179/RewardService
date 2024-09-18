package com.infosys.retailApp.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infosys.retailApp.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, BigInteger>{

}
