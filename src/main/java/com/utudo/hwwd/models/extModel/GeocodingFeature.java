package com.utudo.hwwd.models.extModel;

import java.util.ArrayList;
import java.util.HashMap;

public class GeocodingFeature {
    private String id;
    private String type;
    private ArrayList<String> place_type;
    private float relevance;
    private HashMap<String,String> properties;
    private String text;
    private String place_name;
    private ArrayList<Float> center;
    private String address;
    private ArrayList<Float> bbox;
    private ArrayList<HashMap<String,String>> context;
    private Geometry geometry;

    public GeocodingFeature(){

    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlace_type(ArrayList<String> place_type) {
        this.place_type = place_type;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public void setCenter(ArrayList<Float> center) {
        this.center = center;
    }

    public void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }

    public void setRelevance(float relevance) {
        this.relevance = relevance;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBbox(ArrayList<Float> bbox) {
        this.bbox = bbox;
    }

    public void setContext(ArrayList<HashMap<String, String>> context) {
        this.context = context;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getPlace_type() {
        return place_type;
    }

    public float getRelevance() {
        return relevance;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Float> getBbox() {
        return bbox;
    }

    public String getPlace_name() {
        return place_name;
    }

    public ArrayList<Float> getCenter() {
        return center;
    }

    public ArrayList<HashMap<String, String>> getContext() {
        return context;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getAddress() {
        return address;
    }


    @Override
    public String toString() {
        return "GeocodingFeature{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", place_type=" + place_type +
                ", relevance=" + relevance +
                ", properties=" + properties +
                ", text='" + text + '\'' +
                ", place_name='" + place_name + '\'' +
                ", center=" + center +
                ", address='" + address + '\'' +
                ", bbox=" + bbox +
                ", context=" + context +
                ", geometry=" + geometry +
                '}';
    }
}
