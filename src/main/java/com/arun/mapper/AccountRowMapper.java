package com.arun.mapper;

import com.arun.model.Account;
import com.arun.model.Container;
import com.arun.model.Site;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Adwiti on 8/30/2018.
 */
public class AccountRowMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet rs, int i) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt(1));
        account.setAccountName(rs.getString(2));
        Site site = new Site();
        site.setId(rs.getString(3));
        site.setName(rs.getString(4));
        account.setSite(site);
        Container container = new Container();
        container.setId(rs.getString(5));
        container.setName(rs.getString(6));
        account.setContainer(container);
        return account;
    }
}
