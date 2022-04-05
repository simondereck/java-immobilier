package com.utudo.hwwd.models.models;

import com.utudo.hwwd.models.Models;

import javax.persistence.*;


@Entity
@Table(name = "hw_estimate_model")
public class EstimateModel extends Models {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String date_mutation;
//            2020-01-07

    private int numero_disposition;
//            #somthing not know

    private String nature_mutation;
//            #vente 买卖类型

    private String valeur_fonciere;
//            #80000 价格

    private String adresse_numero;
//        # 11 RUE
    private String adresse_suffixe;
//    # 5A 3B RUE

    private String adresse_nom_voie;
//    #RUE NOM

    private String adresse_code_voie;
//    #NOT SO IMPORT B112

    private String code_postal;
//    #75001 OR SIZE<5 ADD 0 BEFORE

    private String nom_commune;
//    #city nom

    private int code_departement;
//            #unkonw colum


    private int nombre_lots;
    //            #unknow colum


    private int code_type_local;
//            # 1 masion 2 appartement 3 dependacnce


    private int surface_reelle_bati;
//    = 0.01 #面积

    private int nombre_pieces_principales;
//            = 0 #几间房

    private String nature_culture;
//            = "" #类型

    private String surface_terrain;
//            = 0.01 #占用面积

    private Double longitude;

    private Double latitude;




    public void setAdresse_nom_voie(String adresse_nom_voie) {
        this.adresse_nom_voie = adresse_nom_voie;
    }


    public void setAdresse_code_voie(String adresse_code_voie) {
        this.adresse_code_voie = adresse_code_voie;
    }



    public void setAdresse_suffixe(String adresse_suffixe) {
        this.adresse_suffixe = adresse_suffixe;
    }

    public void setDate_mutation(String date_mutation) {
        this.date_mutation = date_mutation;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public void setNature_mutation(String nature_mutation) {
        this.nature_mutation = nature_mutation;
    }


    public void setNom_commune(String nom_commune) {
        this.nom_commune = nom_commune;
    }


    public void setSurface_terrain(String surface_terrain) {
        this.surface_terrain = surface_terrain;
    }

    public void setNumero_disposition(int numero_disposition) {
        this.numero_disposition = numero_disposition;
    }

    public void setNombre_pieces_principales(int nombre_pieces_principales) {
        this.nombre_pieces_principales = nombre_pieces_principales;
    }

    public void setNombre_lots(int nombre_lots) {
        this.nombre_lots = nombre_lots;
    }


    public void setSurface_reelle_bati(int surface_reelle_bati) {
        this.surface_reelle_bati = surface_reelle_bati;
    }

    public void setCode_departement(int code_departement) {
        this.code_departement = code_departement;
    }


    public void setNature_culture(String nature_culture) {
        this.nature_culture = nature_culture;
    }




    public void setAdresse_numero(String adresse_numero) {
        this.adresse_numero = adresse_numero;
    }



    public void setValeur_fonciere(String valeur_fonciere) {
        this.valeur_fonciere = valeur_fonciere;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCode_type_local(int code_type_local) {
        this.code_type_local = code_type_local;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    public String getValeur_fonciere() {
        return valeur_fonciere;
    }




    public String getAdresse_numero() {
        return adresse_numero;
    }




    public String getAdresse_code_voie() {
        return adresse_code_voie;
    }


    public String getAdresse_nom_voie() {
        return adresse_nom_voie;
    }

    public String getAdresse_suffixe() {
        return adresse_suffixe;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public String getDate_mutation() {
        return date_mutation;
    }


    public String getNature_mutation() {
        return nature_mutation;
    }


    public String getNom_commune() {
        return nom_commune;
    }



    public String getSurface_terrain() {
        return surface_terrain;
    }



    public String getNature_culture() {
        return nature_culture;
    }


    public long getId() {
        return id;
    }

    public int getCode_type_local() {
        return code_type_local;
    }


    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public int getNumero_disposition() {
        return numero_disposition;
    }


    public int getNombre_pieces_principales() {
        return nombre_pieces_principales;
    }

    public int getNombre_lots() {
        return nombre_lots;
    }


    public int getCode_departement() {
        return code_departement;
    }


    public int getSurface_reelle_bati() {
        return surface_reelle_bati;
    }

}
