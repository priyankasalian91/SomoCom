package com.aipl.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
//public class StoreApplication {
public class StoreApplication  extends SpringBootServletInitializer {	

    /**
     * Used when run as JAR
     * 
     */
    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }

    /**
     * Used when run as WAR
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(StoreApplication.class);
    
    }

  }
