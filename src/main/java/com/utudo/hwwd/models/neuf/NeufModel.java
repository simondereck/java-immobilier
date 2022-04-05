package com.utudo.hwwd.models.neuf;

import javax.persistence.*;


@Entity
@Table(name = "hw_neuf_model")
public class NeufModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private String title;

    private String subtitle;

    private String cover;

    private String link;

    private String date;

    private String statusString;

    private String images;

    private String address;

    private String addressFull;

    private long ville;

    private String description;

    private Integer status;

    private String utime;

    private String ctime;


    public void setAddressFull(String addressFull) {
        this.addressFull = addressFull;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setVille(long ville) {
        this.ville = ville;
    }

    public long getId() {
        return id;
    }

    public String getUtime() {
        return utime;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public Integer getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }

    public String getCtime() {
        return ctime;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public long getVille() {
        return ville;
    }

    public String getImages() {
        return images;
    }

    public String getDate() {
        return date;
    }

    public String getLink() {
        return link;
    }

    public String getStatusString() {
        return statusString;
    }

    public String getAddressFull() {
        return addressFull;
    }
}
