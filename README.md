# SpringJdbcTemplateVsNamedParameterJdbcTemplate
 
## Configuring Data Source for both JdbcTemplate and NamedParameterJdbcTemplate

### Properties files defined 

    mysql.datasource.url: jdbc:mysql://localhost:3306/tcrmd00
    mysql.datasource.username: root
    mysql.datasource.password: root
    mysql.datasource.driver-class-name: com.mysql.jdbc.Driver
    
### Configuration file defined as below
    package com.arun.config;
    
    import org.springframework.beans.factory.annotation.Qualifier;
    import org.springframework.boot.context.properties.ConfigurationProperties;
    import org.springframework.boot.jdbc.DataSourceBuilder;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
    
    import javax.sql.DataSource;
    
    /**
     * Created by Adwiti on 8/4/2018.
     */
    @Configuration
    public class DataSourceConfig {
    
        @Bean(name = "mysqlDatasource")
        @ConfigurationProperties(prefix = "mysql.datasource")
        public DataSource dataSource() {
            return DataSourceBuilder.create().build();
        }
    
        @Bean(name = "mysqlNamedParameterJdbcTemplate")
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("mysqlDatasource") DataSource mysqlDataSource) {
            return new NamedParameterJdbcTemplate(mysqlDataSource);
        }
    
        @Bean(name = "mysqlJdbcTemplate")
        public JdbcTemplate jdbcTemplate(@Qualifier("mysqlDatasource") DataSource mysqlDataSource) {
            return new JdbcTemplate(mysqlDataSource);
        }
    }
