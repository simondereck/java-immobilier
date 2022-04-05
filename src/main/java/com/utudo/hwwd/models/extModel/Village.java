package com.utudo.hwwd.models.extModel;

public class Village {
    private String codei;
    private String name;
    private String codep;
    private String libelle;
    private String ligne;
    private String gps;


    public void setCodei(String codei) {
        this.codei = codei;
    }

    public void setCodep(String codep) {
        this.codep = codep;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setLigne(String ligne) {
        this.ligne = ligne;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodei() {
        return codei;
    }

    public String getCodep() {
        return codep;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getLigne() {
        return ligne;
    }

    public String getName() {
        return name;
    }

    public String getGps() {
        return gps;
    }

    @Override
    public String toString() {
        return "Village{" +
                "codei='" + codei + '\'' +
                ", name='" + name + '\'' +
                ", codep='" + codep + '\'' +
                ", libelle='" + libelle + '\'' +
                ", ligne='" + ligne + '\'' +
                ", gps='" + gps + '\'' +
                '}';
    }
}
