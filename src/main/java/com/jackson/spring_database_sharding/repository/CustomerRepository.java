/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.spring_database_sharding.repository;

import com.jackson.spring_database_sharding.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * CustomerRepository Class.
 * <p>
 * </p>
 *
 * @author
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    String deleteByCustomerId(String customerName);

    Optional<CustomerEntity> findByCustomerId(String customerId);
}
