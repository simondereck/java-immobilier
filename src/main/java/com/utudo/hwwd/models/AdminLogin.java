package com.utudo.hwwd.models;


import javax.persistence.*;


@Entity
@Table(name = "hw_admin_login")
public class AdminLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public long uid;
    public String etime; //记录时间
    public String date; //日期
    public String ctime; //创建时间


    public long getId() {
        return id;
    }


    public long getUid() {
        return uid;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }


    public void setCtime(String ctime) {
        this.ctime = ctime;
    }


    public void setEtime(String etime) {
        this.etime = etime;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public String getEtime() {
        return etime;
    }

    public String getDate() {
        return date;
    }


    public String getCtime() {
        return ctime;
    }


}
