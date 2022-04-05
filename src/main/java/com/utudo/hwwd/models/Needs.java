package com.utudo.hwwd.models;


import javax.persistence.*;
import java.util.HashMap;

@Entity
@Table(name = "hw_needs")
public class Needs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",unique = true, nullable = false)
    private long id;

    private Float budget;
    private Integer location;
    private String sdate;
    private Integer rtype;
    private Integer trans;
    private long uid;
    private Integer status;
    private String ctime;
    private String utime;
    private String immsJson;
    private String envirJson;

    @Transient
    private HashMap<Integer,Integer> envir = new HashMap<>();

    @Transient
    private HashMap<Integer,Integer> base = new HashMap<>();



    public void setImmsJson(String immsJson) {
        this.immsJson = immsJson;
    }

    public void setBase(HashMap<Integer, Integer> base) {
        this.base = base;
    }

    public void setEnvir(HashMap<Integer, Integer> envir) {
        this.envir = envir;
    }

    public void setEnvirJson(String envirJson) {
        this.envirJson = envirJson;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    public void setTrans(Integer trans) {
        this.trans = trans;
    }

    public void setRtype(Integer rtype) {
        this.rtype = rtype;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }


    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }


    public Integer getLocation() {
        return location;
    }


    public Float getBudget() {
        return budget;
    }

    public String getSdate() {
        return sdate;
    }

    public Integer getTrans() {
        return trans;
    }

    public Integer getRtype() {
        return rtype;
    }

    public long getUid() {
        return uid;
    }


    public String getCtime() {
        return ctime;
    }

    public String getUtime() {
        return utime;
    }

    public Integer getStatus() {
        return status;
    }

    public String getImmsJson() {
        return immsJson;
    }

    public HashMap<Integer, Integer> getBase() {
        return base;
    }

    public HashMap<Integer, Integer> getEnvir() {
        return envir;
    }

    public String getEnvirJson() {
        return envirJson;
    }

    @Override
    public String toString() {
        return "Needs{" +
                "id=" + id +
                ", budget=" + budget +
                ", location=" + location +
                ", sdate='" + sdate + '\'' +
                ", rtype=" + rtype +
                ", trans=" + trans +
                ", uid=" + uid +
                ", status=" + status +
                ", ctime='" + ctime + '\'' +
                ", utime='" + utime + '\'' +
                ", immsJson='" + immsJson + '\'' +
                ", envirJson='" + envirJson + '\'' +
                ", envir=" + envir +
                ", base=" + base +
                '}';
    }
}
