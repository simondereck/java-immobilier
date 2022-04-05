package com.utudo.hwwd.models;

import javax.persistence.*;

@Entity
@Table(name = "hw_estimation_upload")
public class EstimationUploadModel extends Models{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private long uid;

    private Integer uType;

    private long pay;

    private String area;

    private long location;

    private String address;

    private Integer middle;

    private String size;

    private Integer types;

    private Integer houseType;

    private Integer roomType;

    private Integer consTime;

    private Integer nouv;

    private Integer lift;

    private Integer balcon;

    private String balconSize;

    private Integer cave;

    private String caveSize;

    private Integer parking;

    private Integer isOwn;

    private  Integer isSell;

    private Integer contactUs;

    private Integer sex;

    private String nom;

    private String prenom;

    private String wechat;

    private String email;

    private String telephone;

    private String Utime;

    private String ctime;

    private String lat;

    private String lng;

    private String price;


    public void setPrice(String price) {
        this.price = price;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setuType(Integer uType) {
        this.uType = uType;
    }

    public void setPay(long pay) {
        this.pay = pay;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setLocation(long location) {
        this.location = location;
    }


    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setUtime(String utime) {
        Utime = utime;
    }

    public void setMiddle(Integer middle) {
        this.middle = middle;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }


    public void setWechat(String wechat) {
        this.wechat = wechat;
    }


    public void setBalcon(Integer balcon) {
        this.balcon = balcon;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }


    public void setCave(Integer cave) {
        this.cave = cave;
    }


    public void setLift(Integer lift) {
        this.lift = lift;
    }

    public void setParking(Integer parking) {
        this.parking = parking;
    }

    public void setCaveSize(String caveSize) {
        this.caveSize = caveSize;
    }

    public void setBalconSize(String balconSize) {
        this.balconSize = balconSize;
    }

    public void setTypes(Integer types) {
        this.types = types;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    public void setConsTime(Integer consTime) {
        this.consTime = consTime;
    }

    public void setContactUs(Integer contactUs) {
        this.contactUs = contactUs;
    }

    public void setIsOwn(Integer isOwn) {
        this.isOwn = isOwn;
    }

    public void setIsSell(Integer isSell) {
        this.isSell = isSell;
    }

    public void setNouv(Integer nouv) {
        this.nouv = nouv;
    }

    public Integer getHouseType() {
        return houseType;
    }

    public long getId() {
        return id;
    }

    public long getLocation() {
        return location;
    }

    public String getArea() {
        return area;
    }

    public String getAddress() {
        return address;
    }

    public long getPay() {
        return pay;
    }

    public long getUid() {
        return uid;
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

    public String getCtime() {
        return ctime;
    }

    public String getUtime() {
        return Utime;
    }

    public String getWechat() {
        return wechat;
    }

    public String getCaveSize() {
        return caveSize;
    }


    public String getBalconSize() {
        return balconSize;
    }


    public String getSize() {
        return size;
    }

    public Integer getMiddle() {
        return middle;
    }

    public Integer getBalcon() {
        return balcon;
    }

    public Integer getSex() {
        return sex;
    }


    public Integer getCave() {
        return cave;
    }

    public Integer getConsTime() {
        return consTime;
    }

    public Integer getContactUs() {
        return contactUs;
    }

    public Integer getIsOwn() {
        return isOwn;
    }

    public Integer getIsSell() {
        return isSell;
    }

    public Integer getLift() {
        return lift;
    }

    public Integer getNouv() {
        return nouv;
    }

    public Integer getParking() {
        return parking;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public Integer getTypes() {
        return types;
    }


    public Integer getuType() {
        return uType;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "EstimationUploadModel{" +
                "id=" + id +
                ", uid=" + uid +
                ", uType=" + uType +
                ", pay=" + pay +
                ", area='" + area + '\'' +
                ", location=" + location +
                ", address='" + address + '\'' +
                ", middle=" + middle +
                ", size='" + size + '\'' +
                ", types=" + types +
                ", houseType=" + houseType +
                ", roomType=" + roomType +
                ", consTime=" + consTime +
                ", nouv=" + nouv +
                ", lift=" + lift +
                ", balcon=" + balcon +
                ", balconSize='" + balconSize + '\'' +
                ", cave=" + cave +
                ", caveSize='" + caveSize + '\'' +
                ", parking=" + parking +
                ", isOwn=" + isOwn +
                ", isSell=" + isSell +
                ", contactUs=" + contactUs +
                ", sex=" + sex +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", wechat='" + wechat + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", Utime='" + Utime + '\'' +
                ", ctime='" + ctime + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}

