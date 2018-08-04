package com.arun.dao;

import com.arun.model.Student;
import com.arun.rc.config.annotation.RsTimeTraker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @RsTimeTraker
    @Override
    public Integer getCountOfList(Integer streetId, Integer age) {
        Integer count = jdbcTemplate.queryForObject("select count(*) from student where streetid = ? and age = ?", new Object[]{streetId, age}, Integer.class);
        logger.info("Count {}", count);
        return count;
    }

    @Override
    public List<Student> getListOfStudent(Integer streetId) {
        return null;
    }
}
