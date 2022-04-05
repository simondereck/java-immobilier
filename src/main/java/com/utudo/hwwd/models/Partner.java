package com.utudo.hwwd.models;

import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.models.Person;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Entity
@Table(name="hw_partner")
public class Partner extends Models {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;


    @Column(name="sex",nullable = false)
    private Integer sex;

    @Column(name="nom",nullable = false)
    private String nom;

    @Column(name="prenom",nullable = false)
    private String prenom;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="telephone")
    private String telephone;

    @Column(name="utime",nullable = false)
    private String utime;

    @Column(name="ctime",nullable = false)
    private String ctime;

    @Column(name = "status",nullable = false)
    private Integer status;

    @Column(name = "cover")
    private String cover;

    @Column(name = "pays")
    private Integer pays;

    @Transient
    private int order;

    @Transient
    private int iStatus;

    @Column(name="ftoken")
    private String ftoken;


    @Transient
    private Annonce annonce;

    @Transient
    private int error;
    @Transient
    private String message;

    @Column(name = "wechat")
    private String wechat;


    public int getOrder() {
        return order;
    }

    public String getCover() {
        return cover;
    }

    public String getWechat() {
        return wechat;
    }

    public Integer getPays() {
        return pays;
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public int getiStatus() {
        return iStatus;
    }

    public String getPassword() {
        return password;
    }

    public String getNom() {
        return nom;
    }

    public Integer getSex() {
        return sex;
    }

    public long getId() {
        return id;
    }

    public String getCtime() {
        return ctime;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getUtime() {
        return utime;
    }


    public String getFtoken() {
        return ftoken;
    }

    public Integer getStatus() {
        return status;
    }

    public int getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public void setOrder(int order) {
        this.order = order;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public void setFtoken(String ftoken) {
        this.ftoken = ftoken;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setError(int error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPays(Integer pays) {
        this.pays = pays;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Boolean login(Partner partner){
        if (password.equalsIgnoreCase(HwTools.hashPassword(partner.getPassword()))){
            //记住我
            Person person = new Person(this);
            person.setType(HwDatas.PERSON_TYPE_PARTNER);
            HttpSession session = HwTools.session();
            session.setAttribute("user", person);
            HwTools.setPersonInCookie(person);
            return true;
        }
        return false;
    }

    public void setiStatus(int iStatus) {
        this.iStatus = iStatus;
    }

    public void signupLogin(){
        HttpSession session = HwTools.session();
        Person person = new Person(this);
        person.setType(HwDatas.PERSON_TYPE_PARTNER);
        session.setAttribute("user",person);
        HwTools.setPersonInCookie(person);

    }

    public void adminLogin(){
        HttpSession session = HwTools.session();
        Person person = new Person(this);
        session.setAttribute("admin",(Person) session.getAttribute("user"));
        session.setAttribute("user",person);
        HwTools.setPersonInCookie(person);
    }


    @Override
    public String toString() {
        return "Partner{" +
                "id=" + id +
                ", sex=" + sex +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", utime='" + utime + '\'' +
                ", ctime='" + ctime + '\'' +
                ", status=" + status +
                ", iStatus=" + iStatus +
                ", ftoken='" + ftoken + '\'' +
                ", annonce=" + annonce +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean isVaild() {

        if (this.nom==null||this.nom.equals("")){
            this.message = "名字不能为空";
            return false;
        }
        if (this.prenom==null||this.prenom.equals("")){
            this.prenom = "姓不能为空";
            return false;
        }

        if(this.password==null||this.password.equals("")){
            this.password = "密码不能为空";
            return false;
        }


        return true;
    }



}
