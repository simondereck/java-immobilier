package com.utudo.hwwd.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "hw_annonce")
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private Float price;
    private Float loyer;
    private Float charges;

    private Integer location;

    private String address;

    private String description;

    private String sdate;
    private String cover;

    private Float size;

    private Integer energy;

    private Integer furniture;


    private Integer rtype;

    private Integer trans;
    private String transation;

    private long pid;
    private Integer status;
    private String ctime;
    private String utime;

    private String imgsJson;
    private String immsJson;

    private String envirJson;

    private long latlng;


    //type of person
    private Integer type;

    @Transient
    private long serial = 10000000;

    @Transient
    private Ville ville;

    @Transient
    private HashMap<Integer,Integer> envir;

    @Transient
    private ArrayList<String> images;

    @Transient
    private HashMap<Integer,Integer> base;


    public String getTransation() {
        return transation;
    }

    public Ville getVille() {
        return ville;
    }

    public long getSerial() {
        return serial;
    }

    public String getCover() {
        return cover;
    }

    public Integer getFurniture() {
        return furniture;
    }

    public HashMap<Integer, Integer> getBase() {
        return base;
    }

    public String getImmsJson() {
        return immsJson;
    }


    public ArrayList<String> getImages() {
        return images;
    }

    public String getEnvirJson() {
        return envirJson;
    }


    public String getImgsJson() {
        return imgsJson;
    }

    public long getId() {
        return id;
    }

    public Float getPrice() {
        return price;
    }

    public String getSdate() {
        return sdate;
    }

    public Integer getRtype() {
        return rtype;
    }

    public Integer getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public Float getCharges() {
        return charges;
    }

    public Float getLoyer() {
        return loyer;
    }


    public Integer getTrans() {
        return trans;
    }

    public Integer getStatus() {
        return status;
    }

    public Float getSize() {
        return size;
    }

    public Integer getEnergy() {
        return energy;
    }

    public long getLatlng() {
        return latlng;
    }

    public HashMap<Integer, Integer> getEnvir() {
        return envir;
    }

    public String getCtime() {
        return ctime;
    }

    public long getPid() {
        return pid;
    }

    public String getDescription() {
        return description;
    }

    public String getUtime() {
        return utime;
    }

    public Integer getType() {
        return type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrice(Float price) {
        this.price = price;
    }


    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public void setLoyer(Float loyer) {
        this.loyer = loyer;
    }

    public void setRtype(Integer rtype) {
        this.rtype = rtype;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCharges(Float charges) {
        this.charges = charges;
    }


    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setTrans(Integer trans) {
        this.trans = trans;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public void setEnvir(HashMap<Integer, Integer> envir) {
        this.envir = envir;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public void setEnvirJson(String envirJson) {
        this.envirJson = envirJson;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public void setImgsJson(String imgsJson) {
        this.imgsJson = imgsJson;
    }

    public void setBase(HashMap<Integer, Integer> base) {
        this.base = base;
    }

    public void setImmsJson(String immsJson) {
        this.immsJson = immsJson;
    }

    public void setLatlng(long latlng) {
        this.latlng = latlng;
    }

    public void setFurniture(Integer furniture) {
        this.furniture = furniture;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setSerial(long serial) {
        this.serial = serial;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setTransation(String transation) {
        this.transation = transation;
    }

    @Override
    public String toString() {
        return "Annonce{" +
                "id=" + id +
                ", price=" + price +
                ", loyer=" + loyer +
                ", charges=" + charges +
                ", location=" + location +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", sdate='" + sdate + '\'' +
                ", cover='" + cover + '\'' +
                ", size=" + size +
                ", energy=" + energy +
                ", furniture=" + furniture +
                ", rtype=" + rtype +
                ", trans=" + trans +
                ", pid=" + pid +
                ", status=" + status +
                ", ctime='" + ctime + '\'' +
                ", utime='" + utime + '\'' +
                ", imgsJson='" + imgsJson + '\'' +
                ", immsJson='" + immsJson + '\'' +
                ", envirJson='" + envirJson + '\'' +
                ", latlng=" + latlng +
                ", serial=" + serial +
                ", ville=" + ville +
                ", envir=" + envir +
                ", images=" + images +
                ", base=" + base +
                '}';
    }
}
