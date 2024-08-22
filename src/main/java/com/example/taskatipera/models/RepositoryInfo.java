package com.example.taskatipera.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RepositoryInfo {

    private String name;
<<<<<<< HEAD
    private Owner owner;
=======
    private Owner  owner;
>>>>>>> new-origin/master
    private List<Branch> branches;
    boolean fork;
}
