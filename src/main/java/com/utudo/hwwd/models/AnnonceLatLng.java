package com.utudo.hwwd.models;

import com.utudo.hwwd.models.extModel.Geometry;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "hw_annonce_lat_lng")
public class AnnonceLatLng {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private long pid;

    private long aid;

    private long bid;

    private String centerJson;

    @Transient
    private ArrayList<Float> center;

    private String bboxJson;

    @Transient
    private ArrayList<Float> bbox;

    private String geometryJson;

    @Transient
    private Geometry geometry;

    @Transient
    private Annonce annonce;


    private String utime;

    private String ctime;

    public long getBid() {
        return bid;
    }

    public String getCtime() {
        return ctime;
    }

    public String getUtime() {
        return utime;
    }

    public long getId() {
        return id;
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public ArrayList<Float> getCenter() {
        return center;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public ArrayList<Float> getBbox() {
        return bbox;
    }

    public long getPid() {
        return pid;
    }

    public long getAid() {
        return aid;
    }

    public String getBboxJson() {
        return bboxJson;
    }

    public String getCenterJson() {
        return centerJson;
    }

    public String getGeometryJson() {
        return geometryJson;
    }

    public void setCenter(ArrayList<Float> center) {
        this.center = center;
    }

    public void setBbox(ArrayList<Float> bbox) {
        this.bbox = bbox;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public void setBboxJson(String bboxJson) {
        this.bboxJson = bboxJson;
    }

    public void setCenterJson(String centerJson) {
        this.centerJson = centerJson;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public void setGeometryJson(String geometryJson) {
        this.geometryJson = geometryJson;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }


    @Override
    public String toString() {
        return "AnnonceLatLng{" +
                "id=" + id +
                ", pid=" + pid +
                ", aid=" + aid +
                ", bid=" + bid +
                ", centerJson='" + centerJson + '\'' +
                ", center=" + center +
                ", bboxJson='" + bboxJson + '\'' +
                ", bbox=" + bbox +
                ", geometryJson='" + geometryJson + '\'' +
                ", geometry=" + geometry +
                ", annonce=" + annonce +
                ", utime='" + utime + '\'' +
                ", ctime='" + ctime + '\'' +
                '}';
    }
}
