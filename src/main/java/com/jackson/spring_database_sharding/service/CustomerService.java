/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.spring_database_sharding.service;

import com.jackson.spring_database_sharding.config.ShardContext;
import com.jackson.spring_database_sharding.config.ShardResolver;
import com.jackson.spring_database_sharding.entity.CustomerEntity;
import com.jackson.spring_database_sharding.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CustomerService Class.
 * <p>
 * </p>
 *
 * @author
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShardResolver shardResolver;

    @Transactional
    public CustomerEntity createCustomer(CustomerEntity customer) {
        try {
            // Generate customer ID if not provided
            if (customer.getCustomerName() == null || customer.getCustomerName().isEmpty()) {
                System.out.println("reach null");
                customer.setCustomerName("default");
            }

            System.out.println("not null");
            // Set shard based on customer ID
            String shardKey = shardResolver.getShardKey(customer.getCustomerName());
            System.out.println("shard key" + shardKey);
            ShardContext.setCurrentThread(shardKey);
            System.out.println("shard context shard key " + ShardContext.getCurrentThread());


            return customerRepository.save(customer);
        } finally {
            ShardContext.removeCurrentThread();
        }

    }
}
