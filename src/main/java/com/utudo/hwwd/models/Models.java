package com.utudo.hwwd.models;

import javax.persistence.Table;

public class Models {
    public  String getTableName(){
        return this.getClass().getAnnotation(Table.class).name();
    }

    public boolean isVaild(){
        return false;
    }
}
