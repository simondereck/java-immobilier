package com.utudo.hwwd.models.searchModel;

import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.AnnonceBussiness;
import com.utudo.hwwd.models.Pays;


public class PaysSearchModel{


    private long id;

    private String name;

    private String code;

    private String zname;

    private int premission;

    public PaysSearchModel(){

    }

    public PaysSearchModel(Pays pays){
        this.id = pays.getId();
        if (pays.getCode()!=null){
            try{
                this.code = pays.getCode();
            }catch (Exception e){

            }
        }
        if (pays.getName()!=null){
            try{
                this.name = pays.getName();
            }catch (Exception e){

            }
        }

        if (pays.getName()!=null){
            try{
                this.name = pays.getName();
            }catch (Exception e){

            }
        }

        if (pays.getZname()!=null){
            try{
                this.zname = pays.getZname();
            }catch (Exception e){

            }
        }

        this.premission = pays.getPremission();
    }

    public String getSql(){
        SqlHelper sqlHelper = new SqlHelper("hw_pays");
        if (id>0){
            sqlHelper.addAndCondition("id",id);
        }
        if (name!=null){
            sqlHelper.addAndLikeCondition("name",name);
        }
        if (zname!=null){
            sqlHelper.addAndLikeCondition("zname",zname);
        }
        if (code!=null){
            sqlHelper.addAndLikeCondition("code",code);
        }
        if (premission>0){
            sqlHelper.addAndCondition("premission",premission);
        }
        return sqlHelper.toSql();
    }


}
