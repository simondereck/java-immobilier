package com.utudo.hwwd.models.residenceModel;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "hw_residence_type")
public class ResidenceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long uid;

    private long rid;

    private Integer type;


    @Transient
    private ArrayList<String> images;


    private String imageJson;

    private Boolean cusine;

    private Boolean bains;

    private Boolean meuble;

    private Integer persons;

    private Integer priceMax;

    private Integer priceMin;

    private Integer minSize;

    private Integer maxSize;

    private Integer salle;

    private String disponibleAt;

    private String utime;

    private String ctime;



    private String description;


    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setDisponibleAt(String disponibleAt) {
        this.disponibleAt = disponibleAt;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public void setBains(Boolean bains) {
        this.bains = bains;
    }


    public void setCusine(Boolean cusine) {
        this.cusine = cusine;
    }

    public void setImageJson(String imageJson) {
        this.imageJson = imageJson;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public void setMeuble(Boolean meuble) {
        this.meuble = meuble;
    }

    public void setMinSize(Integer minSize) {
        this.minSize = minSize;
    }

    public void setPersons(Integer persons) {
        this.persons = persons;
    }

    public void setPriceMax(Integer priceMax) {
        this.priceMax = priceMax;
    }

    public void setPriceMin(Integer priceMin) {
        this.priceMin = priceMin;
    }

    public void setSalle(Integer salle) {
        this.salle = salle;
    }

    public long getUid() {
        return uid;
    }

    public long getId() {
        return id;
    }

    public Integer getType() {
        return type;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public long getRid() {
        return rid;
    }

    public String getDescription() {
        return description;
    }



    public Boolean getBains() {
        return bains;
    }

    public Boolean getCusine() {
        return cusine;
    }

    public Boolean getMeuble() {
        return meuble;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public Integer getMinSize() {
        return minSize;
    }

    public Integer getPersons() {
        return persons;
    }

    public Integer getPriceMax() {
        return priceMax;
    }

    public Integer getPriceMin() {
        return priceMin;
    }

    public Integer getSalle() {
        return salle;
    }


    public String getImageJson() {
        return imageJson;
    }


    public String getDisponibleAt() {
        return disponibleAt;
    }

    public String getCtime() {
        return ctime;
    }

    public String getUtime() {
        return utime;
    }

    @Override
    public String toString() {
        return "ResidenceType{" +
                "id=" + id +
                ", uid=" + uid +
                ", rid=" + rid +
                ", type=" + type +
                ", images=" + images +
                ", imageJson='" + imageJson + '\'' +
                ", cusine=" + cusine +
                ", bains=" + bains +
                ", meuble=" + meuble +
                ", persons=" + persons +
                ", priceMax=" + priceMax +
                ", priceMin=" + priceMin +
                ", minSize=" + minSize +
                ", maxSize=" + maxSize +
                ", salle=" + salle +
                ", disponibleAt='" + disponibleAt + '\'' +
                ", utime='" + utime + '\'' +
                ", ctime='" + ctime + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
