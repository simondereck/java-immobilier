package com.utudo.hwwd.models;


import javax.persistence.*;

@Entity
@Table(name = "hw_apply_info")
public class ApplyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;


    private String nom;

    private String prenom;

    private String telephone;

    private String wechat;

    private String email;

    private int type;

    private int status;

    private int iStatus;


    private String utime;

    private String ctime;


    public int getiStatus() {
        return iStatus;
    }

    public long getId() {
        return id;
    }

    public String getCtime() {
        return ctime;
    }

    public String getUtime() {
        return utime;
    }

    public String getWechat() {
        return wechat;
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

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getType() {
        return type;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
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

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public void setiStatus(int iStatus) {
        this.iStatus = iStatus;
    }
}
