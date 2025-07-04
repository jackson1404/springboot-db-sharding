/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.spring_database_sharding.controller;

import com.jackson.spring_database_sharding.entity.CustomerEntity;
import com.jackson.spring_database_sharding.service.CustomerService;
import org.hibernate.annotations.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * CustomerController Class.
 * <p>
 * </p>
 *
 * @author
 */

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/createCustomer")
    public CustomerEntity createCustomer(@RequestBody CustomerEntity customer){
        CustomerEntity customerEntity = customerService.createCustomer(customer);
        return customerEntity;
    }

    @GetMapping("/getCustomerById")
    public CustomerEntity getCustomerById(@RequestParam String customerId){
        return customerService.findCustomerById(customerId);
    }


    @PutMapping("/updateCustomerByName")
    public CustomerEntity updateCustomerByName(@RequestParam("customerId") String customerId, @RequestBody CustomerEntity customer){

        return customerService.updateCustomer(customerId, customer);
    }

    @DeleteMapping("/deleteCustomer")
    public String deleteCustomer(@RequestParam("customerId") String customerId){
        return customerService.deleteCustomer(customerId);
    }

}
