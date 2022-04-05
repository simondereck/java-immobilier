package com.utudo.hwwd.models;

import javax.persistence.*;

@Entity
@Table(name="hw_cobber_shop")
public class CobberShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private long cid;

    private String name;

    private String telephone;

    private String email;

    private String address;

    private long type;

    private long status;

    @Transient
    private int iStatus;

    private String description;

    private String utime;

    private String ctime;


    public void setiStatus(int iStatus) {
        this.iStatus = iStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(long type) {
        this.type = type;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getUtime() {
        return utime;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getCtime() {
        return ctime;
    }

    public String getEmail() {
        return email;
    }

    public long getCid() {
        return cid;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public long getType() {
        return type;
    }

    public long getStatus() {
        return status;
    }

    public int getiStatus() {
        return iStatus;
    }

    @Override
    public String toString() {
        return "CobberShop{" +
                "id=" + id +
                ", cid=" + cid +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", iStatus=" + iStatus +
                ", description='" + description + '\'' +
                ", utime='" + utime + '\'' +
                ", ctime='" + ctime + '\'' +
                '}';
    }
}
