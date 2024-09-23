package com.infosys.retailApp.repository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infosys.retailApp.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID>{


	/**Find all the transaction for a give customer.
	 * @param customerId
	 * @param date
	 * @return
	 */
	List<Transaction> findByCustomerIdAndDateAfter(BigInteger customerId, LocalDateTime date);
	
}
