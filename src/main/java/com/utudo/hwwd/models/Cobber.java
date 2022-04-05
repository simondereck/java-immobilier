package com.utudo.hwwd.models;

import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.models.Person;

import javax.persistence.*;
import javax.servlet.http.HttpSession;

@Entity
@Table(name="hw_cobber")
public class Cobber{
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

    @Column(name = "type")
    private Integer type;

    @Column(name="utime",nullable = false)
    private String utime;

    @Column(name="ctime",nullable = false)
    private String ctime;

    @Column(name = "status",nullable = false)
    private Integer status;

    @Column(name = "wechat")
    private String wechat;

    @Transient
    private int iStatus;

    @Column(name="ftoken")
    private String ftoken;



    @Transient
    private int error;
    @Transient
    private String message;

    public String getWechat() {
        return wechat;
    }

    public Integer getType() {
        return type;
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

    public void setType(Integer type) {
        this.type = type;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public boolean login(Cobber cobber){
        if (password.equalsIgnoreCase(HwTools.hashPassword(cobber.getPassword()))){
            //记住我
            Person person = new Person(this);
            person.setType(HwDatas.PERSON_TYPE_COBBER);
            HttpSession session = HwTools.session();
            session.setAttribute("user", person);
            return true;
        }
        return false;
    }

    public void setiStatus(int iStatus) {
        this.iStatus = iStatus;
    }


    public void adminLogin(){
        HttpSession session = HwTools.session();
        Person person = new Person(this);
        session.setAttribute("admin",(Person) session.getAttribute("user"));
        session.setAttribute("user",person);
    }


    public void signupLogin(){
        HttpSession session = HwTools.session();
        Person person = new Person(this);
        person.setType(HwDatas.PERSON_TYPE_COBBER);
        session.setAttribute("user",person);
    }


    @Override
    public String toString() {
        return "Cobber{" +
                "id=" + id +
                ", sex=" + sex +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", type=" + type +
                ", utime='" + utime + '\'' +
                ", ctime='" + ctime + '\'' +
                ", status=" + status +
                ", iStatus=" + iStatus +
                ", ftoken='" + ftoken + '\'' +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
