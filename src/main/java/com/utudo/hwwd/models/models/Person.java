package com.utudo.hwwd.models.models;

import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.models.Admin;
import com.utudo.hwwd.models.Cobber;
import com.utudo.hwwd.models.Partner;
import com.utudo.hwwd.models.User;

public class Person {

    private long id;
    private String prenom;
    private String name;
    private String email;
    private String telephone;
    private String password;
    private String wechat;

    private int sex;
    private int type;
    private int permission;
    private int status;

    private String token;


    public String getPrenom() {
        return prenom;
    }

    public String getToken() {
        return token;
    }


    public long getId() {
        return id;
    }

    public String getWechat() {
        return wechat;
    }

    public int getSex() {
        return sex;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public int getPermission() {
        return permission;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Person(){

    }
    public Person(Cobber cobber){
        this.id = cobber.getId();
        this.name = cobber.getNom();
        this.prenom = cobber.getPrenom();
        this.email = cobber.getEmail();
        this.telephone = cobber.getTelephone();
        this.type = HwDatas.PERSON_TYPE_COBBER;
        this.status = cobber.getStatus();
        this.wechat = cobber.getWechat();
    }

    public Person(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.telephone = user.getTelephone();
        this.prenom = user.getPrenom();
        this.name = user.getNom();
        this.type = HwDatas.PERSON_TYPE_USER;
        this.status = user.getStatus();
        this.wechat = user.getWechat();
    }

    public Person(Partner partner){
        this.id = partner.getId();
        this.email = partner.getEmail();
        this.telephone = partner.getTelephone();
        this.name = partner.getNom();
        this.prenom = partner.getPrenom();
        this.type = HwDatas.PERSON_TYPE_PARTNER;
        this.status = partner.getStatus();
        this.wechat = partner.getWechat();
    }

    public Person(Admin admin){
        this.id = admin.getId();
        this.email = admin.getEmail();
        this.telephone  = admin.getTelephone();
        this.name = admin.getUsername();
        this.permission = admin.getPermission();
        this.type = HwDatas.PERSON_TYPE_ADMIN;
        this.status = admin.getAstatus();
        this.token = admin.getToken();
    }

    public Person loadFormUser(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.telephone = user.getTelephone();
        this.prenom = user.getPrenom();
        this.name = user.getNom();
        this.type = HwDatas.PERSON_TYPE_USER;
        this.status = user.getStatus();
        this.wechat = user.getWechat();
        return this;
    }


    public Person loadFormPartner(Partner partner){
        this.id = partner.getId();
        this.email = partner.getEmail();
        this.telephone = partner.getTelephone();
        this.prenom = partner.getPrenom();
        this.name = partner.getNom();
        this.type = HwDatas.PERSON_TYPE_PARTNER;
        this.status = partner.getStatus();
        this.wechat = partner.getWechat();
        return this;
    }


    public Person loadFormAdmin(Admin admin){
        this.id = admin.getId();
        this.email = admin.getEmail();
        this.telephone  = admin.getTelephone();
        this.name = admin.getUsername();
        this.permission = admin.getPermission();
        this.type = HwDatas.PERSON_TYPE_ADMIN;
        this.status = admin.getAstatus();
        this.token = admin.getToken();
        return this;
    }

}
