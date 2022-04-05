package com.utudo.hwwd.models;


import javax.persistence.*;

@Entity
@Table(name = "hw_bussiness_demander")
public class DemandeBussiness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long pid;

    private String budget;

    private String area;

    private long location;

    private float size;

    private int payMethod;

    private int profession;

    private int buyTime;

    private int purpose;

    private int sex;

    private String prenom;

    private String nom;

    private String email;

    private String telephone;

    private String wechat;

    private String utime;

    private String ctime;

    private int status;


    public void setPid(long pid) {
        this.pid = pid;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setArea(String area) {
        this.area = area;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public void setBuyTime(int buyTime) {
        this.buyTime = buyTime;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setProfession(int profession) {
        this.profession = profession;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setPurpose(int purpose) {
        this.purpose = purpose;
    }

    public long getId() {
        return id;
    }

    public long getLocation() {
        return location;
    }

    public String getBudget() {
        return budget;
    }

    public float getSize() {
        return size;
    }

    public String getArea() {
        return area;
    }

    public String getWechat() {
        return wechat;
    }

    public String getCtime() {
        return ctime;
    }

    public String getUtime() {
        return utime;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public int getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public int getSex() {
        return sex;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getBuyTime() {
        return buyTime;
    }

    public int getProfession() {
        return profession;
    }

    public int getPurpose() {
        return purpose;
    }

    public long getPid() {
        return pid;
    }
}

