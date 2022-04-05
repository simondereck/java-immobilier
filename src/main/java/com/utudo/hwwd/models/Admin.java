package com.utudo.hwwd.models;

import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.models.Person;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import javax.websocket.ClientEndpoint;

@Entity
@Table(name="hw_admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;



    @Column(name = "username",nullable = false)
    private String username;

    @Column(name = "permission",nullable = false)
    private Integer permission;

    @Column(name = "email",nullable = false)
    private String email;


    @Column(name = "password" , nullable = false)
    private String password;

    private String ip;

    @Column(name = "telephone" , nullable = false)
    private String telephone;

    @Column(name = "astatus", nullable = false)
    private Integer astatus;

    @Column(name = "ctime", nullable = false)
    private String ctime;

    @Column(name = "utime",nullable = false)
    private String utime;

    @Column(name = "token")
    private String token;


    private Double loginLat;
    private Double loginLng;
    private Double backupLng;
    private Double backupLat;

    private String loginAddress;
    private String loginPostcode;

    private String backupAddress;
    private String backupPostcode;


    public Double getBackupLat() {
        return backupLat;
    }

    public Double getBackupLng() {
        return backupLng;
    }


    public Double getLoginLat() {
        return loginLat;
    }


    public Double getLoginLng() {
        return loginLng;
    }

    public String getBackupAddress() {
        return backupAddress;
    }


    public String getBackupPostcode() {
        return backupPostcode;
    }


    public String getLoginAddress() {
        return loginAddress;
    }


    public String getLoginPostcode() {
        return loginPostcode;
    }


    public void setBackupAddress(String backupAddress) {
        this.backupAddress = backupAddress;
    }


    public void setBackupPostcode(String backupPostcode) {
        this.backupPostcode = backupPostcode;
    }

    public void setBackupLat(Double backupLat) {
        this.backupLat = backupLat;
    }

    public void setBackupLng(Double backupLng) {
        this.backupLng = backupLng;
    }

    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
    }

    public void setLoginLat(Double loginLat) {
        this.loginLat = loginLat;
    }

    public void setLoginLng(Double loginLng) {
        this.loginLng = loginLng;
    }

    public void setLoginPostcode(String loginPostcode) {
        this.loginPostcode = loginPostcode;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAstatus(Integer astatus) {
        this.astatus = astatus;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getIp() {
        return ip;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Integer getPermission() {
        return permission;
    }

    public String getUtime() {
        return utime;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }


    public String getCtime() {
        return ctime;
    }

    public Integer getAstatus() {
        return astatus;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", permission=" + permission +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", ip='" + ip + '\'' +
                ", telephone='" + telephone + '\'' +
                ", astatus=" + astatus +
                ", ctime='" + ctime + '\'' +
                ", utime='" + utime + '\'' +
                '}';
    }

    public Boolean login(Admin admin){
        if (password.equalsIgnoreCase(HwTools.hashPassword(admin.getPassword()))){
            //是否记住我
            HttpSession session = HwTools.session();
            Person person = new Person(this);
            person.setType(HwDatas.PERSON_TYPE_ADMIN);
            session.setAttribute("user", person);
            return true;
        }
        return false;
    }

    public void signupLogin(){
        HttpSession session = HwTools.session();
        Person person = new Person(this);
        person.setType(HwDatas.PERSON_TYPE_ADMIN);
        session.setAttribute("user",person);
    }


}

