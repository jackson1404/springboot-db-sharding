/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.spring_database_sharding.utility;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * GenerateId Class.
 * <p>
 * </p>
 *
 * @author
 */
@Component
public class GenerateId {

    public String generateCustomerId(){
        int random = ThreadLocalRandom.current().nextInt(100000, 999999);
        return "C-" + random;
    }

}
