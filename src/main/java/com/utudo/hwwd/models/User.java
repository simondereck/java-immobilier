package com.utudo.hwwd.models;

import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.models.Person;

import javax.persistence.*;
import javax.servlet.http.HttpSession;

@Entity
@Table(name="hw_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name = "nom",nullable = false)
    private String nom;

    @Column(name = "prenom",nullable = false)
    private String prenom;

    @Column(name="identity",nullable = false)
    private Integer identity;

    @Column(name="address",nullable = false)
    private String address;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Column(name="birthday")
    private String birthday;

    @Column(name = "status",nullable = false)
    private Integer status;


    @Column(name="password",nullable = false)
    private String password;

    @Column(name = "ftoken")
    private String ftoken;


    @Column(name = "salary")
    private Integer salary;

    @Column(name = "ctime",nullable = false)
    private String ctime;

    @Column(name = "utime",nullable = false)
    private String utime;

    @Column(name = "sex")
    private Integer sex;

    @Column(name = "pays")
    private Integer pays;

    @Transient
    private int iStatus;

    @Transient
    private Boolean remember;
    @Transient
    private int error;
    @Transient
    private String message;


    private String wechat;

    public String getWechat() {
        return wechat;
    }

    public int getiStatus() {
        return iStatus;
    }

    public Boolean getRemember() {
        return remember;
    }

    public long getId() {
        return id;
    }


    public String getNom() {
        return nom;
    }


    public String getPrenom() {
        return prenom;
    }

    public Integer getIdentity() {
        return identity;
    }


    public Integer getPays() {
        return pays;
    }

    public Integer getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }


    public String getBirthday() {
        return birthday;
    }


    public String getCtime() {
        return ctime;
    }

    public String getEmail() {
        return email;
    }

    public String getFtoken() {
        return ftoken;
    }

    public Integer getSalary() {
        return salary;
    }

    public String getPassword() {
        return password;
    }


    public String getTelephone() {
        return telephone;
    }

    public String getUtime() {
        return utime;
    }

    public String getMessage() {
        return message;
    }

    public int getError() {
        return error;
    }

    public Integer getSex() {
        return sex;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setiStatus(int iStatus) {
        this.iStatus = iStatus;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFtoken(String ftoken) {
        this.ftoken = ftoken;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setRemember(Boolean remember) {
        this.remember = remember;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setError(int error) {
        this.error = error;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setPays(Integer pays) {
        this.pays = pays;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", identity=" + identity +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                ", status=" + status +
                ", password='" + password + '\'' +
                ", ftoken='" + ftoken + '\'' +
                ", salary=" + salary +
                ", ctime='" + ctime + '\'' +
                ", utime='" + utime + '\'' +
                ", sex=" + sex +
                ", remember=" + remember +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }

    public Boolean login(User user){
        if (password.equalsIgnoreCase(HwTools.hashPassword(user.getPassword()))){
            //是否记住我
            HttpSession session = HwTools.session();
            Person person = new Person(this);
            person.setType(HwDatas.PERSON_TYPE_USER);
            session.setAttribute("user", person);
            return true;
        }
        return false;
    }

    public void signupLogin(){
        HttpSession session = HwTools.session();
        Person person = new Person(this);
        person.setType(HwDatas.PERSON_TYPE_USER);
        session.setAttribute("user",person);
    }

    public void adminLogin(){
        HttpSession session = HwTools.session();
        Person person = new Person(this);
        session.setAttribute("admin",(Person) session.getAttribute("user"));
        session.setAttribute("user",person);
    }
}
