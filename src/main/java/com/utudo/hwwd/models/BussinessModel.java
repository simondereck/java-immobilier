package com.utudo.hwwd.models;


import javax.persistence.*;


@Entity
@Table(name = "hw_bussiness_model")
public class BussinessModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private long uid;


    private long type;


    private String utime;

    private String ctime;




    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(long type) {
        this.type = type;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public long getUid() {
        return uid;
    }

    public String getUtime() {
        return utime;
    }

    public long getId() {
        return id;
    }

    public long getType() {
        return type;
    }

    public String getCtime() {
        return ctime;
    }

}
