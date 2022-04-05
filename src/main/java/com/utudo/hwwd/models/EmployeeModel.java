package com.utudo.hwwd.models;

import javax.persistence.*;

@Entity
@Table(name = "hw_admin_employee_model")
public class EmployeeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private String date;

    private long uid;

    private String utime;

    private String ctime;

    private String detail;

    private int opt;

    private int breaktimes;


    public void setBreaktimes(int breaktimes) {
        this.breaktimes = breaktimes;
    }


    public void setOpt(int opt) {
        this.opt = opt;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setId(long id) {
        this.id = id;
    }


    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }


    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public long getUid() {
        return uid;
    }

    public long getId() {
        return id;
    }

    public String getCtime() {
        return ctime;
    }

    public String getUtime() {
        return utime;
    }

    public String getDetail() {
        return detail;
    }


    public int getBreaktimes() {
        return breaktimes;
    }


    public int getOpt() {
        return opt;
    }


}
