package com.arun.controller;

import com.arun.model.Account;
import com.arun.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Adwiti on 8/30/2018.
 */
@RestController
public class AccountController {
    @Autowired
    private StudentService studentService;

    @GetMapping("accounts/v1/account/{id}")
    public ResponseEntity<List<Account>> getAccount(@PathVariable Integer id) {
        final List<Account> accounts = studentService.getAccounts(id);
        ResponseEntity<List<Account>> responseEntity = new ResponseEntity<>(accounts, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("accounts/v1/account/extractor/{id}")
    public ResponseEntity<List<Account>> getAccountUsingExtractor(@PathVariable Integer id) {
        final List<Account> accounts = studentService.getAccountsWithExtractor(id);
        ResponseEntity<List<Account>> responseEntity = new ResponseEntity<>(accounts, HttpStatus.OK);
        return responseEntity;
    }
}
