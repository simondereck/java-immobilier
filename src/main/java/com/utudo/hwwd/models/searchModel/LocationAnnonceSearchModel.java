package com.utudo.hwwd.models.searchModel;

import com.utudo.hwwd.helpers.SqlHelper;

public class LocationAnnonceSearchModel{

    private Float maxLng;
    private Float minLng;

    private Float maxLat;
    private Float minLat;


    public Float getMaxLat() {
        return maxLat;
    }

    public Float getMaxLng() {
        return maxLng;
    }

    public Float getMinLng() {
        return minLng;
    }

    public Float getMinLat() {
        return minLat;
    }

    public void setMaxLat(Float maxLat) {
        this.maxLat = maxLat;
    }

    public void setMaxLng(Float maxLng) {
        this.maxLng = maxLng;
    }

    public void setMinLng(Float minLng) {
        this.minLng = minLng;
    }

    public void setMinLat(Float minLat) {
        this.minLat = minLat;
    }


    public String toSql(){
        SqlHelper sqlHelper = new SqlHelper("hw_ville");
        sqlHelper.addAndBetteweenCondition("lat",minLat,maxLat);
        sqlHelper.addAndBetteweenCondition("lng",minLng,maxLng);
        sqlHelper.setLimit(" ");
        return sqlHelper.toSql();
    }

    @Override
    public String toString() {
        return "LocationAnnonceSearchModel{" +
                "maxLng=" + maxLng +
                ", minLng=" + minLng +
                ", maxLat=" + maxLat +
                ", minLat=" + minLat +
                '}';
    }
}
