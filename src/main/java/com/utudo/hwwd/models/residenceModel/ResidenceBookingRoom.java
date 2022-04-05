package com.utudo.hwwd.models.residenceModel;


import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "hw_residence_booking_room")
public class ResidenceBookingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long pid;

    private long rid;

    private long hid;

    private String cover;

    private long uid; //用户id



    private String message;


    private String entree;

    private Integer month;

    private String utime;

    private String ctime;

    private Integer status;



    @Transient
    private ArrayList<Integer> users;

    private String userJson;


    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setUserJson(String userJson) {
        this.userJson = userJson;
    }

    public void setUsers(ArrayList<Integer> users) {
        this.users = users;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }


    public void setPid(long pid) {
        this.pid = pid;
    }


    public void setId(long id) {
        this.id = id;
    }


    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }




    public void setEntree(String entree) {
        this.entree = entree;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public void setHid(long hid) {
        this.hid = hid;
    }

    public long getId() {
        return id;
    }

    public long getUid() {
        return uid;
    }

    public String getUtime() {
        return utime;
    }

    public String getMessage() {
        return message;
    }

    public String getEntree() {
        return entree;
    }

    public String getCtime() {
        return ctime;
    }




    public long getRid() {
        return rid;
    }

    public long getHid() {
        return hid;
    }

    public long getPid() {
        return pid;
    }


    public Integer getMonth() {
        return month;
    }

    public ArrayList<Integer> getUsers() {
        return users;
    }


    public String getUserJson() {
        return userJson;
    }

    public String getCover() {
        return cover;
    }

    public Integer getStatus() {
        return status;
    }
}
