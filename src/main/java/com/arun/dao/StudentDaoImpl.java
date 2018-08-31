package com.arun.dao;

import com.arun.mapper.AccountRowMapper;
import com.arun.model.Account;
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

    @Override
    public List<Account> getAccounts(Integer id) {
        final List<Account> accounts = namedParameterJdbcTemplate.query("select * from account", new AccountRowMapper());
        return accounts;
    }
}
