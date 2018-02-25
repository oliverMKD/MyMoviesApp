package com.oliver.mymovies.klasi;

import java.io.Serializable;

/**
 * Created by Oliver on 2/18/2018.
 */

public class Crew implements Serializable {

    public String department;
    public String job;
   public String name;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
