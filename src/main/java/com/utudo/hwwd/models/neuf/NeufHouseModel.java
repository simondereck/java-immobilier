package com.utudo.hwwd.models.neuf;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
@Table(name = "hw_neuf_house_model")
public class NeufHouseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private long nid;

    private String duree;

    private int pieces;

    @Transient
    private ArrayList<Double> surface;

    private String surfaceString;

    private Integer etage;

    private String composition;


    private String prixString;

    private String Exposition;

    private String prix;

    private String utime;

    private String ctime;


    @Transient
    private HashMap<String,Object> description;

    private String descriptionString;

    public void setDescription(HashMap<String, Object> description) {
        this.description = description;
    }

    public void setDescriptionString(String descriptionString) {
        this.descriptionString = descriptionString;
    }

    public void setExposition(String exposition) {
        Exposition = exposition;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNid(long nid) {
        this.nid = nid;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public void setEtage(Integer etage) {
        this.etage = etage;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public void setSurface(ArrayList<Double> surface) {
        this.surface = surface;
    }

    public void setSurfaceString(String surfaceString) {
        this.surfaceString = surfaceString;
    }

    public void setPrixString(String prixString) {
        this.prixString = prixString;
    }

    public String getCtime() {
        return ctime;
    }

    public long getId() {
        return id;
    }

    public String getUtime() {
        return utime;
    }

    public ArrayList<Double> getSurface() {
        return surface;
    }

    public Integer getEtage() {
        return etage;
    }


    public String getComposition() {
        return composition;
    }


    public String getDuree() {
        return duree;
    }


    public String getPrix() {
        return prix;
    }

    public String getPrixString() {
        return prixString;
    }

    public long getNid() {
        return nid;
    }

    public String getSurfaceString() {
        return surfaceString;
    }

    public int getPieces() {
        return pieces;
    }

    public String getExposition() {
        return Exposition;
    }


    public HashMap<String, Object> getDescription() {
        return description;
    }

    public String getDescriptionString() {
        return descriptionString;
    }
}
