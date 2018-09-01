package com.arun.mapper;

import com.arun.model.Account;
import com.arun.model.Container;
import com.arun.model.Site;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Adwiti on 9/1/2018.
 */
public class AccountResultExtractor implements ResultSetExtractor<List<Account>> {
    @Override
    public List<Account> extractData(ResultSet rs) throws SQLException, DataAccessException {

        Map<String, Account> accountMap = new HashMap<>();
        Map<String, List<Site>> siteMap = new HashMap<>();
        List<Site> sites = new ArrayList<>();
        List<Container> containers = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        Site site = new Site();

        int count = 0;
        while (rs.next()) {
            count++;

            final String accountId = rs.getString(1);
            final String siteId = rs.getString(3);

            boolean isAccountIdPresent = accountMap.containsKey(accountId);
            final boolean isSitePresent = siteMap.containsKey(siteId);

            if (!isAccountIdPresent) {
                /**
                 * if its a new account
                 */
                account = new Account();
                account = getAccountDetails(rs, account);
                accountMap.put(accountId, account);
                sites = new ArrayList<>();
                site = new Site();
                getSiteDetails(rs, site);

                Container container = getContainerDetails(rs);
                containers = new ArrayList<>();
                containers.add(container);

                site.setContainers(containers);
            } else if (isSitePresent) {

                /**
                 * Site already present
                 */

                Container container = getContainerDetails(rs);
                containers.add(container);

                site.setContainers(containers);
                boolean isSitePresentInList = sites.contains(site);

                if (isSitePresentInList) {
                    sites.remove(site);
                }
            } else {

                /**
                 * Its a new Site
                 */
                site = new Site();
                site = getSiteDetails(rs, site);
                containers = new ArrayList<>();
                Container container = getContainerDetails(rs);
                containers.add(container);
                site.setContainers(containers);

            }

            sites.add(site);
            siteMap.put(siteId, sites);
            account.setSites(sites);
            final boolean isAccountPresent = accounts.contains(account);
            if (isAccountPresent) {
                accounts.remove(account);
            }
            accounts.add(account);
        }
        return accounts;
    }

    private Account getAccountDetails(ResultSet rs, Account account) throws SQLException {
        account.setId(rs.getInt(1));
        account.setAccountName(rs.getString(2));
        return account;
    }

    private Site getSiteDetails(ResultSet rs, Site site) throws SQLException {
        site.setId(rs.getString(3));
        site.setName(rs.getString(4));
        return site;
    }

    private Container getContainerDetails(ResultSet rs) throws SQLException {
        Container container = new Container();
        container.setId(rs.getString(5));
        container.setName(rs.getString(6));
        return container;
    }

}
