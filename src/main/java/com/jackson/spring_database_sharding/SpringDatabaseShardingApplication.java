package com.jackson.spring_database_sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringDatabaseShardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDatabaseShardingApplication.class, args);
	}

}
