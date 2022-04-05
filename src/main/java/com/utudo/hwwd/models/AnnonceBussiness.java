package com.utudo.hwwd.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;


@Entity
@Table(name = "hw_annonce_bussiness")
public class AnnonceBussiness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private long pid;


    private long pay;

    private String area;

    private long location;

    private String address;

    private String size;

    private String middle;

    private String price;

    private int types;

    private int houseType;

    private int roomType;


    private String lift;

    private String balcon;


    private String balconSize;

    private String cave;

    private String caveSize;


    private String parking;

    private String description;

    private  String trans;


    @Transient
    private ArrayList<String> images;

    private String imgsJson;

    private String cover;

    private int houseLabel;

    private int sex;

    private String nom;

    private String prenom;

    private String wechat;


    private String email;

    private String telephone;

    private long latlng;

    private int status;


    private String utime;

    private String ctime;

    @Transient
    private long serial = 10000000;


    public long getLatlng() {
        return latlng;
    }

    public long getSerial() {
        return serial;
    }

    public String getBalcon() {
        return balcon;
    }

    public String getCave() {
        return cave;
    }

    public String getLift() {
        return lift;
    }

    public String getParking() {
        return parking;
    }

    public long getLocation() {
        return location;
    }

    public long getId() {
        return id;
    }


    public long getPid() {
        return pid;
    }

    public String getArea() {
        return area;
    }

    public long getPay() {
        return pay;
    }

    public String getAddress() {
        return address;
    }

    public String getWechat() {
        return wechat;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getCover() {
        return cover;
    }

    public String getDescription() {
        return description;
    }

    public String getSize() {
        return size;
    }

    public int getSex() {
        return sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public String getEmail() {
        return email;
    }



    public int getStatus() {
        return status;
    }

    public String getUtime() {
        return utime;
    }


    public String getCtime() {
        return ctime;
    }

    public String getMiddle() {
        return middle;
    }

    public int getTypes() {
        return types;
    }

    public int getHouseType() {
        return houseType;
    }

    public int getHouseLabel() {
        return houseLabel;
    }



    public int getRoomType() {
        return roomType;
    }

    public String getBalconSize() {
        return balconSize;
    }

    public String getCaveSize() {
        return caveSize;
    }

    public String getImgsJson() {
        return imgsJson;
    }

    public String getPrice() {
        return price;
    }

    public String getTrans() {
        return trans;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setPay(long pay) {
        this.pay = pay;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setHouseType(int houseType) {
        this.houseType = houseType;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public void setBalconSize(String balconSize) {
        this.balconSize = balconSize;
    }


    public void setCaveSize(String caveSize) {
        this.caveSize = caveSize;
    }

    public void setHouseLabel(int houseLabel) {
        this.houseLabel = houseLabel;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public void setImgsJson(String imgsJson) {
        this.imgsJson = imgsJson;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public void setLift(String lift) {
        this.lift = lift;
    }

    public void setCave(String cave) {
        this.cave = cave;
    }

    public void setBalcon(String balcon) {
        this.balcon = balcon;
    }


    public void setSerial(long serial) {
        this.serial = serial;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }


    public void setLatlng(long latlng) {
        this.latlng = latlng;
    }

    @Override
    public String toString() {
        return "AnnonceBussiness{" +
                "id=" + id +
                ", pid=" + pid +
                ", pay=" + pay +
                ", area='" + area + '\'' +
                ", location=" + location +
                ", address='" + address + '\'' +
                ", size='" + size + '\'' +
                ", middle='" + middle + '\'' +
                ", price='" + price + '\'' +
                ", types=" + types +
                ", houseType=" + houseType +
                ", roomType=" + roomType +
                ", lift='" + lift + '\'' +
                ", balcon='" + balcon + '\'' +
                ", balconSize='" + balconSize + '\'' +
                ", cave='" + cave + '\'' +
                ", caveSize='" + caveSize + '\'' +
                ", parking='" + parking + '\'' +
                ", description='" + description + '\'' +
                ", trans='" + trans + '\'' +
                ", images=" + images +
                ", imgsJson='" + imgsJson + '\'' +
                ", cover='" + cover + '\'' +
                ", houseLabel=" + houseLabel +
                ", sex=" + sex +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", wechat='" + wechat + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", latlng=" + latlng +
                ", status=" + status +
                ", utime='" + utime + '\'' +
                ", ctime='" + ctime + '\'' +
                ", serial=" + serial +
                '}';
    }
}
