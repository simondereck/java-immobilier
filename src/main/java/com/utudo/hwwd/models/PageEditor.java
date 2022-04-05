package com.utudo.hwwd.models;


import javax.persistence.*;

@Entity
@Table(name = "hw_pages")
public class PageEditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",unique = true, nullable = false)
    private long id;

    private int ptype;


    private String description;

    private String utime;

    private String ctime;

    private int iStatus;



    public void setId(long id) {
        this.id = id;
    }

    public void setPtype(int ptype) {
        this.ptype = ptype;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setiStatus(int iStatus) {
        this.iStatus = iStatus;
    }

    public String getUtime() {
        return utime;
    }

    public long getId() {
        return id;
    }


    public int getPtype() {
        return ptype;
    }

    public String getDescription() {
        return description;
    }

    public String getCtime() {
        return ctime;
    }

    public int getiStatus() {
        return iStatus;
    }


    @Override
    public String toString() {
        return "PageEditor{" +
                "id=" + id +
                ", ptype=" + ptype +
                ", description='" + description + '\'' +
                ", utime='" + utime + '\'' +
                ", ctime='" + ctime + '\'' +
                ", iStatus=" + iStatus +
                '}';
    }
}
