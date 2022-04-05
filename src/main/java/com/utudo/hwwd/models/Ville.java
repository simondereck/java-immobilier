package com.utudo.hwwd.models;

import javax.persistence.*;

@Entity
@Table(name = "hw_ville")
public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private String name;

    private String label;

    private Integer code;

    private String postcode;

    private String lat;

    private String lng;


    private String ctime;

    private String utime;


    public String getLabel() {
        return label;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public String getUtime() {
        return utime;
    }


    public String getCtime() {
        return ctime;
    }

    public long getId() {
        return id;
    }

    public Integer getCode() {
        return code;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Ville{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", code=" + code +
                ", postcode='" + postcode + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", ctime='" + ctime + '\'' +
                ", utime='" + utime + '\'' +
                '}';
    }
}
