package com.utudo.hwwd.models;

import javax.persistence.*;

@Entity
@Table(name = "hw_web_message")
public class WebMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private String message;

    private int status;

    private String utime;

    private String ctime;

    private long toAid;

    private long fromAid;


    public void setMessage(String message) {
        this.message = message;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setToAid(long toAid) {
        this.toAid = toAid;
    }

    public void setFromAid(long fromAid) {
        this.fromAid = fromAid;
    }

    public String getMessage() {
        return message;
    }

    public long getId() {
        return id;
    }

    public String getUtime() {
        return utime;
    }

    public String getCtime() {
        return ctime;
    }

    public int getStatus() {
        return status;
    }

    public long getFromAid() {
        return fromAid;
    }

    public long getToAid() {
        return toAid;
    }


}
