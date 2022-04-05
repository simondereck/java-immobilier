package com.utudo.hwwd.models;

import javax.persistence.*;


@Entity
@Table(name="hw_admin_location")
public class AdminLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private long aid;

    private String ip;

    private float lat;

    private float lng;

    private String utime;

    private String stime;

    private String etime;

    private String date;


    public void setAid(long aid) {
        this.aid = aid;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDate() {
        return date;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }



    public String getEtime() {
        return etime;
    }

    public long getAid() {
        return aid;
    }

    public long getId() {
        return id;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public String getIp() {
        return ip;
    }

    public String getStime() {
        return stime;
    }


    public String getUtime() {
        return utime;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
