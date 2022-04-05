package com.utudo.hwwd.models.searchModel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SearchModel {
    public void loadModel(Object o){
        Field[] oFields = o.getClass().getDeclaredFields();
        for (int i=0;i<oFields.length;i++){
            String name = oFields[i].getName();
            Object value = getFieldValueByName(oFields[i].getName(), o);
            if (value!=null){
                try{
                    Field f = this.getClass().getDeclaredField(name);
                    f.setAccessible(true);
                    f.set(this,value);
                }catch (Exception e){
                    continue;
                }
            }
        }
    }


    private void setFieldValueByFieldName(String fieldName, Object object,String value) {
        try {
            // 获取obj类的字节文件对象
            Class c = object.getClass();
            Field f = c.getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(object, value);
        } catch (Exception e) {

        }
    }

    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            return null;
        }
    }
}
