package com.utudo.hwwd.models.models;

public class CodeSms {
    private String code;
    private String ctime;
    private String utime;
    private String prix;
    private String telephone;

    private Integer type;

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCtime() {
        return ctime;
    }

    public String getUtime() {
        return utime;
    }

    public Integer getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getPrix() {
        return prix;
    }

    public String getTelephone() {
        return telephone;
    }
}
