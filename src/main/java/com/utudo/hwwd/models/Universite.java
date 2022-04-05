package com.utudo.hwwd.models;

import javax.persistence.*;

@Entity
@Table(name = "hw_universite")
public class Universite {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private String city;

    private String name;

    private String profession;

    private String ville;

    private String postcode;

    private double lat;

    private double lng;

    private String utime;

    private String ctime;


    public long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String  getProfession() {
        return profession;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }


    public String getPostcode() {
        return postcode;
    }

    public String getVille() {
        return ville;
    }

    public String getCtime() {
        return ctime;
    }

    public String getUtime() {
        return utime;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }


    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }


    @Override
    public String toString() {
        return "Universite{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", name='" + name + '\'' +
                ", profession='" + profession + '\'' +
                ", ville='" + ville + '\'' +
                ", postcode='" + postcode + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", utime='" + utime + '\'' +
                ", ctime='" + ctime + '\'' +
                '}';
    }
}

