package com.utudo.hwwd.models.extModel;

import java.util.ArrayList;

public class Geometry {
    private String type;
    private ArrayList<Float> coordinates;

    public void setType(String type) {
        this.type = type;
    }

    public void setCoordinates(ArrayList<Float> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public ArrayList<Float> getCoordinates() {
        return coordinates;
    }
}
