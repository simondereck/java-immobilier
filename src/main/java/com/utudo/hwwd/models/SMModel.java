package com.utudo.hwwd.models;

import javax.persistence.*;

@Entity
@Table(name = "hw_sm_model")
public class SMModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    private String sendFrom;
    private String sendTo;
    private int type;//system 0 , user 1
    private String message;
    private String utime;
    private String ctime;
    private String token;
    private int read;



    public String getToken() {
        return token;
    }

    public long getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getUtime() {
        return utime;
    }

    public String getSendFrom() {
        return sendFrom;
    }

    public String getSendTo() {
        return sendTo;
    }

    public String getCtime() {
        return ctime;
    }


    public String getMessage() {
        return message;
    }

    public int getRead() {
        return read;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setType(int type) {
        this.type = type;
    }


    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setSendFrom(String sendFrom) {
        this.sendFrom = sendFrom;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRead(int read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "SMModel{" +
                "id=" + id +
                ", sendFrom='" + sendFrom + '\'' +
                ", sendTo='" + sendTo + '\'' +
                ", type=" + type +
                ", message='" + message + '\'' +
                ", utime='" + utime + '\'' +
                ", ctime='" + ctime + '\'' +
                ", token='" + token + '\'' +
                ", read=" + read +
                '}';
    }
}
