package com.utudo.hwwd.models.extModel;

import java.util.ArrayList;



public class MapBoxGeocodingResponse {
    private String type;
    private ArrayList<String> query;
    private ArrayList<GeocodingFeature> features;

    public void setType(String type) {
        this.type = type;
    }

    public void setFeatures(ArrayList<GeocodingFeature> features) {
        this.features = features;
    }

    public void setQuery(ArrayList<String> query) {
        this.query = query;
    }

    public ArrayList<GeocodingFeature> getFeatures() {
        return features;
    }

    public ArrayList<String> getQuery() {
        return query;
    }

    public String getType() {
        return type;
    }


    @Override
    public String toString() {
        return "MapBoxGeocodingResponse{" +
                "type='" + type + '\'' +
                ", query=" + query +
                ", features=" + features +
                '}';
    }
}
