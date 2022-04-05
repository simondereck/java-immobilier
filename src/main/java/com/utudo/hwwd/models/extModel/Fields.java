package com.utudo.hwwd.models.extModel;

import java.util.List;

public class Fields {

    private String acad;

    private String lib_uai;

    private String groupe_cnu;

    private String code_commune;

    private String commune;

    private List<Double> geolocalisation;


    public String getAcad() {
        return acad;
    }


    public String getLib_uai() {
        return lib_uai;
    }


    public String getCode_commune() {
        return code_commune;
    }


    public String getGroupe_cnu() {
        return groupe_cnu;
    }


    public List<Double> getGeolocalisation() {
        return geolocalisation;
    }


    public String getCommune() {
        return commune;
    }


    public void setAcad(String acad) {
        this.acad = acad;
    }

    public void setCode_commune(String code_commune) {
        this.code_commune = code_commune;
    }

    public void setGroupe_cnu(String groupe_cnu) {
        this.groupe_cnu = groupe_cnu;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public void setLib_uai(String lib_uai) {
        this.lib_uai = lib_uai;
    }

    public void setGeolocalisation(List<Double> geolocalisation) {
        this.geolocalisation = geolocalisation;
    }

    @Override
    public String toString() {
        return "Fields{" +
                "acad='" + acad + '\'' +
                ", lib_uai='" + lib_uai + '\'' +
                ", groupe_cnu='" + groupe_cnu + '\'' +
                ", code_commune='" + code_commune + '\'' +
                ", commune='" + commune + '\'' +
                ", geolocalisation=" + geolocalisation +
                '}';
    }
}
