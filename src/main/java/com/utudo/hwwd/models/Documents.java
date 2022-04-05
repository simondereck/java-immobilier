package com.utudo.hwwd.models;

import javax.persistence.*;


@Entity
@Table(name = "hw_documents")
public class Documents {


    //    1 学生:护照，学校录取(或学生证)，税单，工资单，签证或长居，担保人材 料
    //    2 工作:护照，工作证明(或工作合同)，税单，工资单，签证或长居，担保人 材料

    public static final int TYPE_STUDNET = 0x000001;
    public static final int TYPE_WORK = 0x000002;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    private long uid;
    private int type;
    private String passport;
    private String certifcation;
    private String tax;
    private String payRoll;
    private String visa;

    private String grantieVisa;
    private String grantiepayRoll;


    private String utime;
    private String ctime;


    public void setId(long id) {
        this.id = id;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setCertifcation(String certifcation) {
        this.certifcation = certifcation;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public void setPayRoll(String payRoll) {
        this.payRoll = payRoll;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setGrantiepayRoll(String grantiepayRoll) {
        this.grantiepayRoll = grantiepayRoll;
    }

    public void setGrantieVisa(String grantieVisa) {
        this.grantieVisa = grantieVisa;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public void setVisa(String visa) {
        this.visa = visa;
    }

    public long getId() {
        return id;
    }

    public long getUid() {
        return uid;
    }

    public String getCertifcation() {
        return certifcation;
    }

    public String getPassport() {
        return passport;
    }

    public String getUtime() {
        return utime;
    }

    public String getCtime() {
        return ctime;
    }

    public String getPayRoll() {
        return payRoll;
    }

    public String getTax() {
        return tax;
    }

    public String getVisa() {
        return visa;
    }

    public int getType() {
        return type;
    }

    public String getGrantiepayRoll() {
        return grantiepayRoll;
    }

    public String getGrantieVisa() {
        return grantieVisa;
    }


    @Override
    public String toString() {
        return "Documents{" +
                "id=" + id +
                ", uid=" + uid +
                ", type=" + type +
                ", passport='" + passport + '\'' +
                ", certifcation='" + certifcation + '\'' +
                ", tax='" + tax + '\'' +
                ", payRoll='" + payRoll + '\'' +
                ", visa='" + visa + '\'' +
                ", grantieVisa='" + grantieVisa + '\'' +
                ", grantiepayRoll='" + grantiepayRoll + '\'' +
                ", utime='" + utime + '\'' +
                ", ctime='" + ctime + '\'' +
                '}';
    }
}
