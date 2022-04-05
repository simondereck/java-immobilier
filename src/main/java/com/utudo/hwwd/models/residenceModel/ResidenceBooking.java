package com.utudo.hwwd.models.residenceModel;


import javax.persistence.*;

@Entity
@Table(name = "hw_residence_booking")
public class ResidenceBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long pid;

    private long rid;

    private long hid;

    private long uid;

    private String type;

    private String message;

    private String entree;

    private Integer month;

    private String nom;

    private String prenom;

    private String email;

    private String telephone;

    private Integer sex;

    @Transient
    private String password;

    private String utime;

    private String ctime;


    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setPassword(String password) {
        this.password = password;
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


    public void setType(String type) {
        this.type = type;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public String getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Integer getSex() {
        return sex;
    }

    public String getTelephone() {
        return telephone;
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

    public String getPassword() {
        return password;
    }

    public Integer getMonth() {
        return month;
    }
}
