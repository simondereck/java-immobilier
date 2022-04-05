package com.utudo.hwwd.models.extModel;

import java.util.List;

public class Zone {

    private String name;

    private List<extPays> countrys;


    public void setName(String name) {
        this.name = name;
    }


    public void setCountrys(List<extPays> countrys) {
        this.countrys = countrys;
    }


    public String getName() {
        return name;
    }


    public List<extPays> getCountrys() {
        return countrys;
    }


}
