package com.arun.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by Adwiti on 8/30/2018.
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {
    private Integer id;
    private String accountName;
    private Site site;
    private Container container;
    private List<Site> sites;
}
