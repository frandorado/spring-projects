package com.frandorado.springbatchawsintegrationslave.bootstrap;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasourceConfiguration {
    
    private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    
    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/db");
        dataSourceBuilder.username("user");
        dataSourceBuilder.password("password");
        dataSourceBuilder.driverClassName(DRIVER_CLASS_NAME);
        
        return dataSourceBuilder.build();
    }
    
}
