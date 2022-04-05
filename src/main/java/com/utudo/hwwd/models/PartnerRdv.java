package com.utudo.hwwd.models;

import javax.persistence.*;

@Entity
@Table(name = "hw_rdv")
public class PartnerRdv {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private long uid;

    private long pid;

    private long aid;

    private long nid;

    private String sdate;

    private String edate;

    private String message;

    private String utime;

    private String ctime;


    public void setMessage(String message) {
        this.message = message;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public void setNid(long nid) {
        this.nid = nid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public long getId() {
        return id;
    }

    public String getEdate() {
        return edate;
    }

    public String getSdate() {
        return sdate;
    }

    public long getPid() {
        return pid;
    }

    public String getUtime() {
        return utime;
    }

    public long getNid() {
        return nid;
    }

    public long getAid() {
        return aid;
    }

    public long getUid() {
        return uid;
    }

    public String getCtime() {
        return ctime;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public String toString() {
        return "PartnerRdv{" +
                "id=" + id +
                ", uid=" + uid +
                ", pid=" + pid +
                ", aid=" + aid +
                ", nid=" + nid +
                ", sdate='" + sdate + '\'' +
                ", edate='" + edate + '\'' +
                ", message='" + message + '\'' +
                ", utime='" + utime + '\'' +
                ", ctime='" + ctime + '\'' +
                '}';
    }
}
