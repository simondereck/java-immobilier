package com.utudo.hwwd.models.models;

import com.utudo.hwwd.models.Models;

import javax.persistence.*;


@Entity
@Table(name = "hw_estimate_model_3")
public class EstimateModel3 extends Models {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String id_mutation;
//            2020-1

    private String date_mutation;
//            2020-01-07

    private String numero_disposition;
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

    private String code_commune;
//        #75101 FOR SOMTHIG BUT DIDN'T USEFURL


    private String nom_commune;
//    #city nom

    private String code_departement;
//            #unkonw colum

    private String ancien_code_commune;
//            #unkonw colum

    private String ancien_nom_commune;
//            #unknow colum

    private String id_parcelle;
//            #unknow colum


    private String ancien_id_parcelle;
//            #unknow colum

    private String numero_volume;
//            #unknow colum


    private String lot1_numero;
    private String lot1_surface_carrez;

    private String lot2_numero;
    private String lot2_surface_carrez;


    private String lot3_numero;
    private String lot3_surface_carrez;

    private String lot4_numero;
    private String lot4_surface_carrez;


    private String lot5_numero;
    private String lot5_surface_carrez;

    private String nombre_lots;
    //            #unknow colum


    private String code_type_local;
//            # 1 masion 2 appartement 3 dependacnce

    private String type_local;
//        # masion appartement

    private String surface_reelle_bati;
//    = 0.01 #面积

    private String nombre_pieces_principales;
//            = 0 #几间房

    private String code_nature_culture;
//            = "" #类型

    private String nature_culture;
//    = ""

    private String code_nature_culture_speciale;
//    = "" #unknow colum

    private String nature_culture_speciale;



    private String surface_terrain;
//            = 0.01 #占用面积

    private String longitude;

    private String latitude;


    public void setId_mutation(String id_mutation) {
        this.id_mutation = id_mutation;
    }

    public void setCode_commune(String code_commune) {
        this.code_commune = code_commune;
    }

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


    public void setAncien_code_commune(String ancien_code_commune) {
        this.ancien_code_commune = ancien_code_commune;
    }

    public void setCode_departement(String code_departement) {
        this.code_departement = code_departement;
    }

    public void setNom_commune(String nom_commune) {
        this.nom_commune = nom_commune;
    }

    public void setAncien_nom_commune(String ancien_nom_commune) {
        this.ancien_nom_commune = ancien_nom_commune;
    }



    public void setAncien_id_parcelle(String ancien_id_parcelle) {
        this.ancien_id_parcelle = ancien_id_parcelle;
    }

    public void setId_parcelle(String id_parcelle) {
        this.id_parcelle = id_parcelle;
    }

    public void setLot1_numero(String lot1_numero) {
        this.lot1_numero = lot1_numero;
    }



    public void setLot2_numero(String lot2_numero) {
        this.lot2_numero = lot2_numero;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLot3_numero(String lot3_numero) {
        this.lot3_numero = lot3_numero;
    }



    public void setLot4_numero(String lot4_numero) {
        this.lot4_numero = lot4_numero;
    }



    public void setLot5_numero(String lot5_numero) {
        this.lot5_numero = lot5_numero;
    }



    public void setNumero_volume(String numero_volume) {
        this.numero_volume = numero_volume;
    }

    public void setCode_nature_culture(String code_nature_culture) {
        this.code_nature_culture = code_nature_culture;
    }

    public void setCode_nature_culture_speciale(String code_nature_culture_speciale) {
        this.code_nature_culture_speciale = code_nature_culture_speciale;
    }

    public void setSurface_terrain(String surface_terrain) {
        this.surface_terrain = surface_terrain;
    }

    public void setSurface_reelle_bati(String surface_reelle_bati) {
        this.surface_reelle_bati = surface_reelle_bati;
    }

    public void setNombre_lots(String nombre_lots) {
        this.nombre_lots = nombre_lots;
    }

    public void setCode_type_local(String code_type_local) {
        this.code_type_local = code_type_local;
    }

    public void setNature_culture(String nature_culture) {
        this.nature_culture = nature_culture;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setNature_culture_speciale(String nature_culture_speciale) {
        this.nature_culture_speciale = nature_culture_speciale;
    }


    public void setNumero_disposition(String numero_disposition) {
        this.numero_disposition = numero_disposition;
    }

    public void setAdresse_numero(String adresse_numero) {
        this.adresse_numero = adresse_numero;
    }

    public void setNombre_pieces_principales(String nombre_pieces_principales) {
        this.nombre_pieces_principales = nombre_pieces_principales;
    }

    

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    public void setValeur_fonciere(String valeur_fonciere) {
        this.valeur_fonciere = valeur_fonciere;
    }

    public void setType_local(String type_local) {
        this.type_local = type_local;
    }

    public void setLot5_surface_carrez(String lot5_surface_carrez) {
        this.lot5_surface_carrez = lot5_surface_carrez;
    }

    public void setLot4_surface_carrez(String lot4_surface_carrez) {
        this.lot4_surface_carrez = lot4_surface_carrez;
    }

    public void setLot3_surface_carrez(String lot3_surface_carrez) {
        this.lot3_surface_carrez = lot3_surface_carrez;
    }

    public void setLot2_surface_carrez(String lot2_surface_carrez) {
        this.lot2_surface_carrez = lot2_surface_carrez;
    }

    public void setLot1_surface_carrez(String lot1_surface_carrez) {
        this.lot1_surface_carrez = lot1_surface_carrez;
    }



    public String getId_mutation() {
        return id_mutation;
    }

    public String getValeur_fonciere() {
        return valeur_fonciere;
    }



    public String getCode_commune() {
        return code_commune;
    }

    public String getAdresse_numero() {
        return adresse_numero;
    }

    public String getNumero_disposition() {
        return numero_disposition;
    }

    public String getCode_type_local() {
        return code_type_local;
    }


    public String getNombre_lots() {
        return nombre_lots;
    }

    public String getNombre_pieces_principales() {
        return nombre_pieces_principales;
    }


    public String getAdresse_code_voie() {
        return adresse_code_voie;
    }

    public String getType_local() {
        return type_local;
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


    public String getCode_departement() {
        return code_departement;
    }


    public String getNature_mutation() {
        return nature_mutation;
    }

    public String getAncien_code_commune() {
        return ancien_code_commune;
    }


    public String getAncien_id_parcelle() {
        return ancien_id_parcelle;
    }

    public String getAncien_nom_commune() {
        return ancien_nom_commune;
    }

    public String getId_parcelle() {
        return id_parcelle;
    }


    public String getLot1_numero() {
        return lot1_numero;
    }

    public String getLot2_numero() {
        return lot2_numero;
    }

    public String getNom_commune() {
        return nom_commune;
    }


    public String getLot3_numero() {
        return lot3_numero;
    }

    public String getLot4_numero() {
        return lot4_numero;
    }

    public String getNumero_volume() {
        return numero_volume;
    }


    public String getSurface_reelle_bati() {
        return surface_reelle_bati;
    }






    public String getCode_nature_culture() {
        return code_nature_culture;
    }

    public String getCode_nature_culture_speciale() {
        return code_nature_culture_speciale;
    }

    public String getLot5_numero() {
        return lot5_numero;
    }


    public String getSurface_terrain() {
        return surface_terrain;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getNature_culture() {
        return nature_culture;
    }

    public String getNature_culture_speciale() {
        return nature_culture_speciale;
    }

    public long getId() {
        return id;
    }

    public String getLot1_surface_carrez() {
        return lot1_surface_carrez;
    }

    public String getLot2_surface_carrez() {
        return lot2_surface_carrez;
    }

    public String getLot3_surface_carrez() {
        return lot3_surface_carrez;
    }

    public String getLot4_surface_carrez() {
        return lot4_surface_carrez;
    }

    public String getLot5_surface_carrez() {
        return lot5_surface_carrez;
    }

    @Override
    public String toString() {
        return "EstimateModel{" +
                "id=" + id +
                ", id_mutation='" + id_mutation + '\'' +
                ", date_mutation='" + date_mutation + '\'' +
                ", numero_disposition='" + numero_disposition + '\'' +
                ", nature_mutation='" + nature_mutation + '\'' +
                ", valeur_fonciere='" + valeur_fonciere + '\'' +
                ", adresse_numero='" + adresse_numero + '\'' +
                ", adresse_suffixe='" + adresse_suffixe + '\'' +
                ", adresse_nom_voie='" + adresse_nom_voie + '\'' +
                ", adresse_code_voie='" + adresse_code_voie + '\'' +
                ", code_postal='" + code_postal + '\'' +
                ", code_commune='" + code_commune + '\'' +
                ", nom_commune='" + nom_commune + '\'' +
                ", code_departement='" + code_departement + '\'' +
                ", ancien_code_commune='" + ancien_code_commune + '\'' +
                ", ancien_nom_commune='" + ancien_nom_commune + '\'' +
                ", id_parcelle='" + id_parcelle + '\'' +
                ", ancien_id_parcelle='" + ancien_id_parcelle + '\'' +
                ", numero_volume='" + numero_volume + '\'' +
                ", lot1_numero='" + lot1_numero + '\'' +
                ", lot1_surface_carrez='" + lot1_surface_carrez + '\'' +
                ", lot2_numero='" + lot2_numero + '\'' +
                ", lot2_surface_carrez='" + lot2_surface_carrez + '\'' +
                ", lot3_numero='" + lot3_numero + '\'' +
                ", lot3_surface_carrez='" + lot3_surface_carrez + '\'' +
                ", lot4_numero='" + lot4_numero + '\'' +
                ", lot4_surface_carrez='" + lot4_surface_carrez + '\'' +
                ", lot5_numero='" + lot5_numero + '\'' +
                ", lot5_surface_carrez='" + lot5_surface_carrez + '\'' +
                ", nombre_lots='" + nombre_lots + '\'' +
                ", code_type_local='" + code_type_local + '\'' +
                ", type_local='" + type_local + '\'' +
                ", surface_reelle_bati='" + surface_reelle_bati + '\'' +
                ", nombre_pieces_principales='" + nombre_pieces_principales + '\'' +
                ", code_nature_culture='" + code_nature_culture + '\'' +
                ", nature_culture='" + nature_culture + '\'' +
                ", code_nature_culture_speciale='" + code_nature_culture_speciale + '\'' +
                ", nature_culture_speciale='" + nature_culture_speciale + '\'' +
                ", surface_terrain='" + surface_terrain + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
