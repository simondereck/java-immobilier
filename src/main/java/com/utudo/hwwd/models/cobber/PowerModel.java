package com.utudo.hwwd.models.cobber;


import javax.persistence.*;

@Entity
@Table(name = "hw_cobber_power")
public class PowerModel {

    public static int POWER_STATUS_START = 0x0001;
    public static int POWER_STATUS_ACCEPT  = 0x0002;
    public static int POWER_STATUS_FINISH  = 0x0003;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private long uid;
    private long cid;


    private String prenom;
    private String nom;
    private int sex;
    private String rib;
    private String wechat;
    private String meters;
    private String address;
    private String telephone;
    private String email;


    private String utime;
    private String ctime;

    private int status;


    public void setCid(long cid) {
        this.cid = cid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public void setMeters(String meters) {
        this.meters = meters;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }



    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public String getPrenom() {
        return prenom;
    }

    public long getId() {
        return id;
    }

    public int getSex() {
        return sex;
    }


    public String getNom() {
        return nom;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }


    public String getEmail() {
        return email;
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

    public int getStatus() {
        return status;
    }

    public long getUid() {
        return uid;
    }

    public String getRib() {
        return rib;
    }

    public String getMeters() {
        return meters;
    }

    public long getCid() {
        return cid;
    }

    @Override
    public String toString() {
        return "PowerModel{" +
                "id=" + id +
                ", uid=" + uid +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", sex=" + sex +
                ", rib='" + rib + '\'' +
                ", wechat='" + wechat + '\'' +
                ", meters='" + meters + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", utime='" + utime + '\'' +
                ", ctime='" + ctime + '\'' +
                ", status=" + status +
                '}';
    }
}
