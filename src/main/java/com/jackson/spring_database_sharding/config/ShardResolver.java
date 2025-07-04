/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.spring_database_sharding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * ShardResolver Class.
 * <p>
 * </p>
 *
 * @author
 */

@Component
public class ShardResolver {

    private static final int SHARD_COUNT = 2; // Number of database shards

    public String getShardKey(String customerId) {
        if (customerId == null ) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }

        // Hash-based sharding using customer ID
        int hash = Math.abs(customerId.hashCode());
        int shardIndex = hash % SHARD_COUNT;

        return "shard" + (shardIndex + 1); // This will return "shard1" or "shard2"
    }


//    public String getShardKey(String customerName) {
//        if (customerName == null || customerName.isEmpty()) {
//            throw new IllegalArgumentException("Customer ID cannot be null or empty");
//        }
//
//        // Hash-based sharding using customer ID
//        int hash = Math.abs(customerName.hashCode());
//        int shardIndex = hash % SHARD_COUNT;
//
//        return "shard" + (shardIndex + 1); // This will return "shard1" or "shard2"
//    }


    public int getShardCount() {
        return SHARD_COUNT;
    }


}
