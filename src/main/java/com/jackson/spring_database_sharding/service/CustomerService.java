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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

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

    @Autowired
    private PlatformTransactionManager transactionManager;
    
    public CustomerEntity createCustomer(CustomerEntity customer) {

        String shardKey = shardResolver.getShardKey(customer.getCustomerName());
        ShardContext.setCurrentThread(shardKey);
        try {
            TransactionTemplate template = new TransactionTemplate(transactionManager);
            return template.execute(status -> customerRepository.save(customer));
        } finally {
            ShardContext.removeCurrentThread();
        }

    }
}
