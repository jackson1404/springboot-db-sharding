/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.spring_database_sharding.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * ShardDatasourceRouter Class.
 * <p>
 * </p>
 *
 * @author
 */

public class ShardDatasourceRouter extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return ShardContext.getCurrentThread();
    }
}
