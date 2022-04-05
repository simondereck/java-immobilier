package com.utudo.hwwd.models.residenceModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;


@Entity
@Table(name = "hw_residence")
public class Residence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Transient
    private long nid;

    private long uid;

    private String cover;

    @Transient
    private ArrayList<String> images;

    private String imgJson;

    private String ville;

    private String code;

    private String name;

    private String address;

    private String brand;

    private Double partir;

    private Double maximun;

    private Boolean available;

    private String disponibleAt;

    private Boolean eligible;//是否申请补助


    private Double sizeMax;

    private Double sizeMin;

    private String description;


    private String info;


    private String chargesJson;


    private String chargesOptionJson;

    @Transient
    private HashMap<Integer,Integer> charges;

    @Transient
    private HashMap<Integer,Integer> chargesOption;

    @Transient
    private HashMap<Integer,Integer> services;

    private String serviceJson;


    @Transient
    private HashMap<Integer,Integer> servicesOption;

    private String serviceOptJson;


    private Integer duree;

    private Integer montant;


    private String utime;


    private String ctime;

    private Integer garante;

    private Integer solvabilite;


    public HashMap<Integer, Integer> getCharges() {
        return charges;
    }


    public HashMap<Integer, Integer> getChargesOption() {
        return chargesOption;
    }


    public String getChargesJson() {
        return chargesJson;
    }

    public String getChargesOptionJson() {
        return chargesOptionJson;
    }

    public Integer getGarante() {
        return garante;
    }

    public Integer getSolvabilite() {
        return solvabilite;
    }

    public long getId() {
        return id;
    }


    public long getUid() {
        return uid;
    }

    public String getCover() {
        return cover;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public Boolean getAvailable() {
        return available;
    }

    public String getVille() {
        return ville;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    public Boolean getEligible() {
        return eligible;
    }


    public String getBrand() {
        return brand;
    }

    public Double getMaximun() {
        return maximun;
    }

    public String getImgJson() {
        return imgJson;
    }

    public String getUtime() {
        return utime;
    }

    public String getCtime() {
        return ctime;
    }

    public Double getPartir() {
        return partir;
    }

    public HashMap<Integer, Integer> getServices() {
        return services;
    }

    public HashMap<Integer, Integer> getServicesOption() {
        return servicesOption;
    }

    public Integer getDuree() {
        return duree;
    }


    public Integer getMontant() {
        return montant;
    }


    public String getDisponibleAt() {
        return disponibleAt;
    }


    public String getInfo() {
        return info;
    }


    public long getNid() {
        return 10000000 + id;
    }

    public String getServiceJson() {
        return serviceJson;
    }

    public String getServiceOptJson() {
        return serviceOptJson;
    }

    public Double getSizeMax() {
        return sizeMax;
    }

    public Double getSizeMin() {
        return sizeMin;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setImgJson(String imgJson) {
        this.imgJson = imgJson;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public void setDisponibleAt(String disponibleAt) {
        this.disponibleAt = disponibleAt;
    }

    public void setEligible(Boolean eligible) {
        this.eligible = eligible;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    public void setMaximun(Double maximun) {
        this.maximun = maximun;
    }

    public void setMontant(Integer montant) {
        this.montant = montant;
    }

    public void setPartir(Double partir) {
        this.partir = partir;
    }

    public void setServiceJson(String serviceJson) {
        this.serviceJson = serviceJson;
    }

    public void setServiceOptJson(String serviceOptJson) {
        this.serviceOptJson = serviceOptJson;
    }

    public void setServices(HashMap<Integer, Integer> services) {
        this.services = services;
    }

    public void setServicesOption(HashMap<Integer, Integer> servicesOption) {
        this.servicesOption = servicesOption;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setSizeMax(Double sizeMax) {
        this.sizeMax = sizeMax;
    }


    public void setSizeMin(Double sizeMin) {
        this.sizeMin = sizeMin;
    }


    public void setGarante(Integer garante) {
        this.garante = garante;
    }

    public void setNid(long nid) {
        this.nid = nid;
    }

    public void setSolvabilite(Integer solvabilite) {
        this.solvabilite = solvabilite;
    }

    public void setCharges(HashMap<Integer, Integer> charges) {
        this.charges = charges;
    }


    public void setChargesJson(String chargesJson) {
        this.chargesJson = chargesJson;
    }

    public void setChargesOption(HashMap<Integer, Integer> chargesOption) {
        this.chargesOption = chargesOption;
    }

    public void setChargesOptionJson(String chargesOptionJson) {
        this.chargesOptionJson = chargesOptionJson;
    }


}
