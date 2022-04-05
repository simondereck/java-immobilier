package com.utudo.hwwd.models.searchModel;

import com.utudo.hwwd.helpers.SqlHelper;

public class MapBussinessSearchModel{

    private long pays;

    private long location;

    private Float price1;
    private Float price2;

    private Float size1;
    private Float size2;

    private int type;

    private int orderType;

    private int orderValue;


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


    public int getOrderType() {
        return orderType;
    }


    public int getOrderValue() {
        return orderValue;
    }

    public String toSql(){
        SqlHelper sqlHelper = new SqlHelper("hw_annonce_bussiness");
        sqlHelper.setLimit(" ");



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



        return sqlHelper.toSql();
    }
}
