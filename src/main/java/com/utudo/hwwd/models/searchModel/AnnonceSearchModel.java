package com.utudo.hwwd.models.searchModel;

import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.Annonce;


public class AnnonceSearchModel {


    private long id;

    private Float price;

    private long base = 10000000;

    private long serial;

    public AnnonceSearchModel(){

    }

    public AnnonceSearchModel(Annonce annonce){
        this.id = annonce.getId();
        this.price = annonce.getPrice();
        this.serial = annonce.getSerial();

    }

    public String getSql(){
        SqlHelper sqlHelper = new SqlHelper("hw_annonce");
        if (id>0){
            sqlHelper.addAndCondition("id",id);
        }else if (price!=null){
            sqlHelper.addAndBetteweenCondition("price",price*0.8,price*1.2);
        }else if (serial>0){
            sqlHelper.addAndCondition("id",(serial - base));
        }
        return sqlHelper.toSql();
    }


}
