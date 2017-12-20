package com.example.blalonde9489.projectapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by blalonde9489 on 10/11/2017.
 */
@Entity
public class User {

    @PrimaryKey
    public final int id;
    public String name;
    public String email;
    public String password;


    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name=name;
        this.email = email;
        this.password  = password;
    }

}
