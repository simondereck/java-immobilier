package com.utudo.hwwd.models;


import javax.persistence.*;

@Entity
@Table(name = "hw_location_region")
public class LocationRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private long code;

    private String lat;

    private String lng;

    private String ctime;

    private String utime;

    public void setCode(long code) {
        this.code = code;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }


    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }


    public long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getLat() {
        return lat;
    }

    public String getUtime() {
        return utime;
    }

    public String getCtime() {
        return ctime;
    }


    public String getLng() {
        return lng;
    }


    @Override
    public String toString() {
        return "LocationRegion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", ctime='" + ctime + '\'' +
                ", utime='" + utime + '\'' +
                '}';
    }
}
