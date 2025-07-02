/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.spring_database_sharding.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * DatasourceConfig Class.
 * <p>
 * </p>
 *
 * @author
 */

@Configuration
@EnableJpaRepositories(
        basePackages = "com.jackson.spring_database_sharding.repository",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class DatasourceConfig {

    @Primary
    @Bean(name = "shard1Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.shard1")
    public DataSource shard1Datasource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "shard2Datasource")
    @ConfigurationProperties(prefix = "spring.datasource.shard2")
    public DataSource shard2Datasource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "routingDatasource")
    public DataSource routingDatasource(){
        ShardDatasourceRouter routingDataSource = new ShardDatasourceRouter();

        Map<Object, Object> datasourceMap = new HashMap<>();
        datasourceMap.put("shard1", shard1Datasource());
        datasourceMap.put("shard2", shard2Datasource());

        routingDataSource.setTargetDataSources(datasourceMap);
        routingDataSource.setDefaultTargetDataSource(shard1Datasource());

        return routingDataSource;
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("routingDatasource") DataSource dataSource) {

        // Define JPA properties explicitly, including ddl-auto
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "update"); // Crucial for table creation/updates
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.format_sql", "true");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        return builder
                .dataSource(dataSource) // Use the routing data source
                .packages("com.jackson.spring_database_sharding.entity") // Package where JPA entities are located
                .persistenceUnit("customer") // Name of the persistence unit
                .properties(jpaProperties) // Apply JPA properties
                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }



}
