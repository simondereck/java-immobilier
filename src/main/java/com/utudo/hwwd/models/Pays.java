package com.utudo.hwwd.models;

import javax.persistence.*;

@Entity
@Table(name = "hw_pays")
public class Pays {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private String name;

    private String code;

    private String zname;

    private String utime;

    private String ctime;

    private int premission;


    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setZname(String zname) {
        this.zname = zname;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setPremission(int premission) {
        this.premission = premission;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getUtime() {
        return utime;
    }

    public String getZname() {
        return zname;
    }

    public String getCtime() {
        return ctime;
    }


    public int getPremission() {
        return premission;
    }
}
