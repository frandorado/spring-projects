package com.frandorado.springbatchintegrationmaster.conf;


import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasourceConfiguration {
    
/*    @Autowired
    ApplicationProperties applicationProperties;
    
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(applicationProperties.getDatasourceUrl())
                .driverClassName(applicationProperties.getDatasourceDriver())
                .username(applicationProperties.getDatasourceUsername())
                .password(applicationProperties.getDatasourcePassword()).build();
        
        *//*BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(applicationProperties.getDatasourceDriver());
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setUrl(applicationProperties.getDatasourceUrl());
        dataSource.setUsername(applicationProperties.getDatasourceUsername());
        dataSource.setPassword(applicationProperties.getDatasourcePassword());
        return dataSource;*//*
    }*/
}
