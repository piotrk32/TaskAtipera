package com.example.taskatipera.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RepositoryInfo {

    private String name;
    private Owner owner;
    private List<Branch> branches;
    boolean fork;
}
