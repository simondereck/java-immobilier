package com.utudo.hwwd.models.searchModel;

import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.Annonce;
import com.utudo.hwwd.models.AnnonceBussiness;


public class AnnonceBussinessSearchModel{


    private long id;

    private Float price;

    private long base = 10000000;

    private long serial;
    
    private Float size;

    public AnnonceBussinessSearchModel(){

    }

    public AnnonceBussinessSearchModel(AnnonceBussiness annonce){
        this.id = annonce.getId();
        if (annonce.getPrice()!=null){
            try{
                this.price = Float.parseFloat(annonce.getPrice());
            }catch (Exception e){

            }
        }

        this.serial = annonce.getSerial();

        if (annonce.getSize()!=null){
            try{
                this.size = Float.parseFloat(annonce.getSize());
            }catch (Exception e){

            }
        }
    }

    public String getSql(){
        SqlHelper sqlHelper = new SqlHelper("hw_annonce_bussiness");
        if (id>0){
            sqlHelper.addAndCondition("id",id);
        }else if (price!=null){
            sqlHelper.addAndBetteweenCondition("price",price*0.8,price*1.2);
        }else if (serial>0){
            sqlHelper.addAndCondition("id",(serial - base));
        }else if (size!=null){
            sqlHelper.addAndBetteweenCondition("size",size*0.8,size*1.2);
        }
        return sqlHelper.toSql();
    }


}
