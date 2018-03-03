package com.oliver.mymovies.klasi;

import java.io.Serializable;

/**
 * Created by Oliver on 2/6/2018.
 */

public class User implements Serializable {

    public String username;
    public String password;
    public String name;
    public Avatar avatar;




    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.avatar = avatar;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
