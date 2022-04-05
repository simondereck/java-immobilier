package com.utudo.hwwd.models;

import javax.persistence.*;

@Entity
@Table(name="hw_alarm")
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    private long totalAnnonces;
    private long successAnnonces;
    private long contractAnnonces;
    private long emptyAnnonces;
    private String utime;
    private String date;
    private String ctime;


    public void setId(long id) {
        this.id = id;
    }

    public void setContractAnnonces(long contractAnnonces) {
        this.contractAnnonces = contractAnnonces;
    }

    public void setEmptyAnnonces(long emptyAnnonces) {
        this.emptyAnnonces = emptyAnnonces;
    }

    public void setSuccessAnnonces(long successAnnonces) {
        this.successAnnonces = successAnnonces;
    }

    public void setTotalAnnonces(long totalAnnonces) {
        this.totalAnnonces = totalAnnonces;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public long getContractAnnonces() {
        return contractAnnonces;
    }

    public long getEmptyAnnonces() {
        return emptyAnnonces;
    }

    public long getSuccessAnnonces() {
        return successAnnonces;
    }

    public long getTotalAnnonces() {
        return totalAnnonces;
    }


    public void setDate(String date) {
        this.date = date;
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

    public String getDate() {
        return date;
    }


    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", totalAnnonces=" + totalAnnonces +
                ", successAnnonces=" + successAnnonces +
                ", contractAnnonces=" + contractAnnonces +
                ", emptyAnnonces=" + emptyAnnonces +
                ", utime='" + utime + '\'' +
                ", date='" + date + '\'' +
                ", ctime='" + ctime + '\'' +
                '}';
    }
}
