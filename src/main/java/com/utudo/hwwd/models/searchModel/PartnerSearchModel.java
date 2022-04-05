package com.utudo.hwwd.models.searchModel;

import com.utudo.hwwd.helpers.SqlHelper;

public class PartnerSearchModel {
    private long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private long type;

    public void setId(long id) {
        this.id = id;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setType(long type) {
        this.type = type;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public long getType() {
        return type;
    }

    public String toSql(){
        SqlHelper sqlHelper = new SqlHelper("hw_partner");
//        if (type>0){
//            sqlHelper.addAndCondition("type",type);
//        }

        if (id>0){
            sqlHelper.addAndCondition("id",id);
        }

        if (nom!=null){
            sqlHelper.addAndLikeCondition("nom",nom);
        }

        if (prenom!=null){
            sqlHelper.addAndLikeCondition("prenom",prenom);
        }


        if (telephone!=null){
            sqlHelper.addAndLikeCondition("telephone",telephone);
        }

        if (email!=null){
            sqlHelper.addAndLikeCondition("email",email);
        }



        return sqlHelper.toSql();
    }
}
