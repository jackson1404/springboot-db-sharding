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

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

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

        UUID customerId = UUID.randomUUID();
        customer.setCustomerId(customerId);
        String shardKey = shardResolver.getShardKey(customerId);
        ShardContext.setCurrentThread(shardKey);
        try {
            TransactionTemplate template = new TransactionTemplate(transactionManager);
            return template.execute(status -> customerRepository.save(customer));
        } finally {
            ShardContext.removeCurrentThread();
        }

    }


//    public CustomerEntity findCustomerById(String customerName) {
//
//        String shardKey = shardResolver.getShardKey(customerName);
//        System.out.println("shard key find id" + shardKey);
//        ShardContext.setCurrentThread(shardKey);
//        try{
//            TransactionTemplate template = new TransactionTemplate(transactionManager);
//            return template.execute( status -> customerRepository.findByCustomerName(customerName)
//                    .orElseThrow(()-> new RuntimeException("Not found")))  ;
//        } finally {
//            ShardContext.removeCurrentThread();
//        }
//
//    }
//
    public CustomerEntity updateCustomer(UUID customerId, CustomerEntity customer) {
        String shardKey = shardResolver.getShardKey(customerId);
        ShardContext.setCurrentThread(shardKey);

        try{
            CustomerEntity customerEntity = customerRepository.findByCustomerId(customerId)
                    .orElseThrow(()-> new RuntimeException("Not found"));

            customerEntity.setCustomerName(customer.getCustomerName());
            customerEntity.setCustomerPhone(customer.getCustomerPhone());

            TransactionTemplate template = new TransactionTemplate(transactionManager);

            return template.execute(status -> customerRepository.save(customerEntity));

        } finally {
            ShardContext.removeCurrentThread();
        }
    }
//
//    public CustomerEntity deleteCustomer(String customerName) {
//        String shardKey = shardResolver.getShardKey(customerName);
//        ShardContext.setCurrentThread(shardKey);
//
//        try {
//            CustomerEntity customer = customerRepository
//                    .findByCustomerName(customerName)
//                    .orElseThrow(() -> new RuntimeException("not found"));
//
//            TransactionTemplate template = new TransactionTemplate(transactionManager);
//            template.execute(status -> {
//                customerRepository.deleteById(customer.getCustomerId());
//                return true;
//            });
//
//            return customer;
//        } finally {
//            ShardContext.removeCurrentThread();
//        }
//    }

}
