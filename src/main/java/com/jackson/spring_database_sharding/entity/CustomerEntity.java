/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.spring_database_sharding.entity;

import com.jackson.spring_database_sharding.repository.CustomerRepository;
import jakarta.persistence.*;

import java.util.UUID;

/**
 * CustomerEntity Class.
 * <p>
 * </p>
 *
 * @author
 */
@Entity
@Table(name = "tbl_customers")
public class CustomerEntity {

    @Id
    @Column(name = "customer_id", nullable = false, updatable = false)
    private UUID customerId;

    private String customerName;
    private String customerPhone;

    public CustomerEntity() {}

    public CustomerEntity(UUID customerId, String customerName, String customerPhone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}
