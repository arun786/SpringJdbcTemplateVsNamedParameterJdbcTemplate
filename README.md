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


### dao layer 

    using jdbcTemplate, the amount of time taken to retrieve count of age and streeid is arounf 2 ms. Total number of records are 500
    
        package com.arun.dao;
        
        import com.arun.model.Student;
        import com.arun.rc.config.annotation.RsTimeTraker;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Qualifier;
        import org.springframework.jdbc.core.JdbcTemplate;
        import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
        import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
        import org.springframework.jdbc.core.namedparam.SqlParameterSource;
        import org.springframework.stereotype.Repository;
        
        import java.util.List;
        
        /**
         * Created by Adwiti on 8/4/2018.
         */
        @Repository
        public class StudentDaoImpl implements StudentDao {
        
            private final Logger logger = LoggerFactory.getLogger(this.getClass());
        
            @Autowired
            @Qualifier("mysqlJdbcTemplate")
            private JdbcTemplate jdbcTemplate;
        
            @Autowired
            @Qualifier("mysqlNamedParameterJdbcTemplate")
            private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        
            @RsTimeTraker
            @Override
            public Integer getCountOfList(Integer streetId, Integer age) {
                Integer count = jdbcTemplate.queryForObject("select count(1) from student where streetid = ? and age = ?", new Object[]{streetId, age}, Integer.class);
                logger.info("Count {}", count);
                return count;
            }
        
            @RsTimeTraker
            @Override
            public Integer getCountOfListWithNamed(Integer streetId, Integer age) {
                SqlParameterSource name = new MapSqlParameterSource().addValue("streetid", streetId).addValue("age", age);
                Integer count = namedParameterJdbcTemplate.queryForObject("select count(1) from student where streetid = :streetid and age = :age", name, Integer.class);
                logger.info("Count {}", count);
                return count;
            }
        
            @RsTimeTraker
            @Override
            public List<Student> getListOfStudent(Integer streetId, Integer age) {
                return jdbcTemplate.query("select * from student where streetid = ? and age = ?", new Object[]{streetId, age}, (rs, rn) -> {
                    Student student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setName(rs.getString("name"));
                    student.setAddress(rs.getString("address"));
                    student.setStreetId(rs.getString("streetId"));
                    return student;
                });
            }
        
            @RsTimeTraker
            @Override
            public List<Student> getListOfStudentWithNamed(Integer streetId, Integer age) {
                SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("streetid", streetId)
                        .addValue("age", age);
                return namedParameterJdbcTemplate.query("select * from student where streetid = :streetid and age = :age", sqlParameterSource, (rs, rn) -> {
                    Student student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setStreetId(rs.getString("streetid"));
                    student.setAddress(rs.getString("address"));
                    student.setName(rs.getString("name"));
                    student.setAge(rs.getString("age"));
                    return student;
                });
            }
        }
