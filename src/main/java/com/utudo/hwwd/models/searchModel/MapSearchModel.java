package com.utudo.hwwd.models.searchModel;

import com.utudo.hwwd.helpers.PageHelper;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.Annonce;
import com.utudo.hwwd.service.impl.AnnonceServiceImpl;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class MapSearchModel{

    private long pays;

    private long location;

    private Float price1;
    private Float price2;

    private Float size1;
    private Float size2;

    private int type;

    private int orderType;

    private int orderValue;

    private int rtype;

    private long page;

    public void setPage(long page) {
        this.page = page;
    }

    public void setRtype(int rtype) {
        this.rtype = rtype;
    }

    public void setOrderValue(int orderValue) {
        this.orderValue = orderValue;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    public void setPays(long pays) {
        this.pays = pays;
    }

    public void setPrice1(Float price1) {
        this.price1 = price1;
    }


    public void setPrice2(Float price2) {
        this.price2 = price2;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSize1(Float size1) {
        this.size1 = size1;
    }

    public void setSize2(Float size2) {
        this.size2 = size2;
    }

    public long getLocation() {
        return location;
    }

    public int getType() {
        return type;
    }

    public Float getPrice1() {
        return price1;
    }


    public Float getPrice2() {
        return price2;
    }


    public Float getSize1() {
        return size1;
    }

    public long getPays() {
        return pays;
    }

    public Float getSize2() {
        return size2;
    }

    public int getRtype() {
        return rtype;
    }


    public long getPage() {
        return page;
    }

    public int getOrderType() {
        return orderType;
    }

    public int getOrderValue() {
        return orderValue;
    }

    public String toSql(){
        SqlHelper sqlHelper = new SqlHelper("hw_annonce");
        sqlHelper.setLimit(" ");
        if (pays>0){
//            sqlHelper.addAndCondition("pays",pays);
        }

        if (price1!=null&&price2!=null){
            sqlHelper.addAndBetteweenCondition("price",price1,price2);
        }else if (price1!=null){
            sqlHelper.addAndCondition(">=","price",price1);
        }else if (price2!=null){
            sqlHelper.addAndCondition("<=","price",price2);
        }

        if (location>0){
            sqlHelper.addAndCondition("location",location);
        }

        if (rtype>0){
            sqlHelper.addAndCondition("rtype",rtype);
        }

        if (size1!=null && size2!=null){
            sqlHelper.addAndBetteweenCondition("size",size1,size2);
        }else if (size1!=null){
            sqlHelper.addAndCondition(">=","size",size1);
        }else if (size2!=null){
            sqlHelper.addAndCondition("<=","size",size2);
        }




        switch (type){
            case 0:
                break;
            default:
                break;
        }

        if (orderType==0){
            if (orderValue==1){
                sqlHelper.setOrder("id","desc");
            }else{
                sqlHelper.setOrder("id","asc");
            }
        }else if (orderType==1){
            if (orderValue==1){
                sqlHelper.setOrder("price","desc");
            }else{
                sqlHelper.setOrder("price","asc");
            }
        }else if (orderType==2){
            if (orderValue==1){
                sqlHelper.setOrder("size","desc");
            }else{
                sqlHelper.setOrder("size","asc");
            }

        }

        sqlHelper.addAndCondition(">","status",0);

        return sqlHelper.toSql();
    }


    public HashMap<String, Object> moreToSql(AnnonceServiceImpl service, String baseUrl){
        HashMap<String,Object> data = new HashMap<>();
        SqlHelper sqlHelper = new SqlHelper("hw_annonce");

        sqlHelper.setSelect("select count(*) ");

//        sqlHelper.setLimit(" ");


        if (price1!=null&&price2!=null){
            sqlHelper.addAndBetteweenCondition("price",price1,price2);
        }else if (price1!=null){
            sqlHelper.addAndCondition(">=","price",price1);
        }else if (price2!=null){
            sqlHelper.addAndCondition("<=","price",price2);
        }

        if (location>0){
            sqlHelper.addAndCondition("location",location);
        }

        if (rtype>0){
            sqlHelper.addAndCondition("rtype",rtype);
        }

        if (size1!=null && size2!=null){
            sqlHelper.addAndBetteweenCondition("size",size1,size2);
        }else if (size1!=null){
            sqlHelper.addAndCondition(">=","size",size1);
        }else if (size2!=null){
            sqlHelper.addAndCondition("<=","size",size2);
        }


        switch (type){
            case 0:
                break;
            default:
                break;
        }

        if (orderType==0){
            if (orderValue==1){
                sqlHelper.setOrder("id","desc");
            }else{
                sqlHelper.setOrder("id","asc");
            }
        }else if (orderType==1){
            if (orderValue==1){
                sqlHelper.setOrder("price","desc");
            }else{
                sqlHelper.setOrder("price","asc");
            }
        }else if (orderType==2){
            if (orderValue==1){
                sqlHelper.setOrder("size","desc");
            }else{
                sqlHelper.setOrder("size","asc");
            }
        }

        sqlHelper.addAndCondition(">","status",0);

        BigInteger count = service.getCountBySql(sqlHelper.toSql());

        if (count!=null && Integer.parseInt(count+"")>0){
            PageHelper pageHelper = new PageHelper();
            pageHelper.setCount(Long.parseLong(count+""));
            pageHelper.setCurrentPage(page);
            pageHelper.setLimit(20);
            pageHelper.setBaseUrl(baseUrl);

            sqlHelper.setSelect("select * ");
            sqlHelper.setPageHelper(pageHelper);

            ArrayList<Annonce> annonces = service.getAnnonceByPage(sqlHelper.toSql());
            if (annonces!=null && annonces.size()>0){
                data.put("annonces",annonces);
                data.put("pageHelper",pageHelper);
                data.put("status",1);
                return data;
            }

        }
        data.put("status",0);
        return data;
    }


}
