package com.utudo.hwwd.models;

import javax.persistence.*;

@Entity
@Table(name = "hw_match_option")
public class MatchOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private long aid;
    private long pid;
    private long nid;

    private Integer status;//1 like 2 dislike

    private String ctime;
    private String utime;

    public long getAid() {
        return aid;
    }

    public Integer getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    public long getNid() {
        return nid;
    }

    public long getPid() {
        return pid;
    }

    public String getCtime() {
        return ctime;
    }

    public String getUtime() {
        return utime;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNid(long nid) {
        this.nid = nid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }


    @Override
    public String toString() {
        return "MatchOption{" +
                "id=" + id +
                ", aid=" + aid +
                ", pid=" + pid +
                ", nid=" + nid +
                ", status=" + status +
                ", ctime='" + ctime + '\'' +
                ", utime='" + utime + '\'' +
                '}';
    }
}
