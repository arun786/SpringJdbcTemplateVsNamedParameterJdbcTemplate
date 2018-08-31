package com.arun.service;

import com.arun.dao.StudentDao;
import com.arun.model.Account;
import com.arun.model.Container;
import com.arun.model.Site;
import com.arun.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Adwiti on 8/4/2018.
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;

    @Override
    public List<Student> getStudents(Integer streetId, Integer age) {
        Integer countOfList = studentDao.getCountOfList(streetId, age);
        if (countOfList == 0) {
            return Arrays.asList();
        }
        return studentDao.getListOfStudent(streetId, age);
    }

    @Override
    public List<Student> getStudentsNamed(Integer streetId, Integer age) {
        Integer countOfListWithNamed = studentDao.getCountOfListWithNamed(streetId, age);
        if (countOfListWithNamed == 0) {
            return Arrays.asList();
        }
        return studentDao.getListOfStudentWithNamed(streetId, age);
    }

    @Override
    public List<Account> getAccounts(Integer id) {
        final List<Account> accounts = studentDao.getAccounts(id);
        final Map<Integer, List<Account>> accountMap = accounts.stream().collect(Collectors.groupingBy(Account::getId));
        List<Account> finalAccounts = new ArrayList<>();
        accountMap.entrySet().stream().forEach(a -> {
            Account account = new Account();
            account.setId(a.getKey());
            account.setAccountName(a.getValue().get(0).getAccountName());
            final List<Account> value = a.getValue();
            final Map<String, List<Account>> siteMap = value.stream().collect(Collectors.groupingBy(acc -> acc.getSite().getId()));
            List<Site> sites = new ArrayList<>();
            siteMap.entrySet().stream().forEach(s -> {
                Site site = new Site();
                final List<Account> value1 = s.getValue();
                site.setId(s.getKey());
                site.setName(s.getValue().get(0).getSite().getName());
                List<Container> containers = new ArrayList<Container>();
                value1.stream().forEach(c -> {
                    Container container = new Container();
                    container.setId(c.getContainer().getId());
                    container.setName(c.getContainer().getName());
                    containers.add(container);
                });
                site.setContainers(containers);
                sites.add(site);
            });
            account.setSites(sites);
            finalAccounts.add(account);
        });
        return finalAccounts;
    }
}
