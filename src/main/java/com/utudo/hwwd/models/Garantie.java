package com.utudo.hwwd.models;

import javax.persistence.*;

@Entity
@Table(name = "hw_garantie")
public class Garantie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name = "uid",nullable = false)
    private long uid;

    @Column(name = "nid",nullable = false)
    private long nid;
    private Integer garantie;
    private Integer job;
    private String salary;
    private String document;
    private Integer payMethod;
    private String ctime;
    private String utime;

    public long getUid() {
        return uid;
    }

    public long getId() {
        return id;
    }

    public long getNid() {
        return nid;
    }

    public Integer getGarantie() {
        return garantie;
    }

    public Integer getJob() {
        return job;
    }

    public String getDocument() {
        return document;
    }

    public String getSalary() {
        return salary;
    }

    public String getUtime() {
        return utime;
    }

    public String getCtime() {
        return ctime;
    }

    public Integer getPayMethod() {
        return payMethod;
    }


    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public void setGarantie(Integer garantie) {
        this.garantie = garantie;
    }

    public void setJob(Integer job) {
        this.job = job;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setNid(long nid) {
        this.nid = nid;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }


    @Override
    public String toString() {
        return "Garantie{" +
                "id=" + id +
                ", uid=" + uid +
                ", nid=" + nid +
                ", garantie=" + garantie +
                ", job=" + job +
                ", salary='" + salary + '\'' +
                ", document='" + document + '\'' +
                ", payMethod=" + payMethod +
                ", ctime='" + ctime + '\'' +
                ", utime='" + utime + '\'' +
                '}';
    }


    public void loadFromObj(Garantie garantie){
        this.garantie = garantie.getGarantie();
        this.job = garantie.getJob();
        this.salary = garantie.getSalary();
        this.document = garantie.getDocument();
        this.payMethod = garantie.getPayMethod();
    }
}
