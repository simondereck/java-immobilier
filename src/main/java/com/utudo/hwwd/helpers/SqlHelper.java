package com.utudo.hwwd.helpers;


import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Objects;

public class SqlHelper {
    private final ArrayList<String> andConditions = new ArrayList<String>();
    private final ArrayList<String> orConditions = new ArrayList<String>();
    private final ArrayList<String> condtions = new ArrayList<String>();
    private String limit = null;
    private String tableName = null;
    private String select = null;
    private String order = null;
    private PageHelper pageHelper = null;


    public void setPageHelper(PageHelper pageHelper) {
        this.pageHelper = pageHelper;
    }

    public void setOrder(String colum, String order) {
        this.order = " order by `"+ colum + "` " + order + " ";
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public SqlHelper(String tableName){
        this.tableName = tableName;
    }
    

    public SqlHelper addAndCondition(String colum, Object value){
         this.andConditions.add("`"+colum + "` = " + value);
         return this;
    }

     public SqlHelper addAndCondition(String option,String colum,Object value){
         this.andConditions.add("`"+colum + "` "+option+" "+ value);
         return this;
     }

     public SqlHelper addOrConditions(String condition){
        this.orConditions.add(condition);
        return this;
     }

     public SqlHelper addOrCondition(String colum, Object value){
         this.orConditions.add("`"+colum + "` = " + value);
         return this;
     }

     public SqlHelper addOrCondition(String option,String colum,Object value){
         this.orConditions.add("`"+colum + "` " + option + " " + value);
         return this;
     }

     public SqlHelper addCondition(String condition){
         this.condtions.add(condition);
         return this;
     }

     public SqlHelper addAndBetteweenCondition(String colum,Object value1,Object value2){
        this.andConditions.add(" ( `" +colum +"` BETWEEN '" + value1 + "' AND '"+ value2 + "') ");
        return this;
     }

     public SqlHelper removeLastAndCondition(){
        if (this.andConditions.size()>0)
            this.andConditions.remove(this.andConditions.size()-1);
        return this;
     }

     public SqlHelper addAndLikeCondition(String colum,Object value){
        this.andConditions.add("`"+colum + "` LIKE  '%"+ value + "%'");
        return this;
     }

    public SqlHelper addOrLikeCondition(String colum,Object value){
        this.orConditions.add("`"+colum + "` LIKE  '%"+ value + "%'");
        return this;
    }

     public SqlHelper setNotIn(String colum,ArrayList<Object> values){
        StringBuilder condition = new StringBuilder("(");
        for (int i = 0; i < values.size(); i++) {
            if (i==values.size()-1){
                condition.append(values.get(i));
            }else{
                condition.append(values.get(i)).append(",");
            }
        }
        condition.append(")");
        this.andConditions.add("`"+colum + "` not in " + condition);
        return this;
     }


     public SqlHelper addInCondition(String colum, ArrayList<Object> values){
        StringBuilder condition = new StringBuilder("(");
        for (int i = 0; i < values.size();i++){
            if (i==values.size()-1){
                condition.append(values.get(i));
            }else{
                condition.append(values.get(i)).append(",");
            }
        }
        condition.append(")");
        this.andConditions.add("`"+colum + "` in " + condition);
        return this;
     }



     public SqlHelper innerJoin(String alias_a,String join_table,String join_alias,String colum1,String colum2,String option) {

        return this;
     }




     public String toSql(){
        String sql = "";
         sql += Objects.requireNonNullElse(select, "select * ");

        if (tableName!=null){
            sql += " from "+tableName;
        }else{
            throw new NullPointerException("TABLE NAME NOT EXIST");
        }


        if (andConditions.size()>0){
            sql += " where ";
            StringBuilder sqlBuilder = new StringBuilder(sql);
            for (int i = 0; i < andConditions.size(); i++) {
                if (i==andConditions.size()-1){
                    sqlBuilder.append(andConditions.get(i)).append(" ");
                }else{
                    sqlBuilder.append(andConditions.get(i)).append(" and ");
                }
            }

            sql = sqlBuilder.toString();
        }

        if (orConditions.size()>0){
            StringBuilder sqlBuilder = new StringBuilder(sql);
            for (String orCondition : orConditions) {
                sqlBuilder.append("or ");
                sqlBuilder.append(orCondition).append(" ");
            }
            sql = sqlBuilder.toString();
        }

        if (order!=null){
            sql += order;
        }

        if (pageHelper!=null){
            sql += " limit "+pageHelper.getLimit() + " offset "+ pageHelper.getOffset();
        }else{
            sql += Objects.requireNonNullElse(limit, " limit 20;");
        }

        return sql;

     }

    @Override
    public String toString() {
        return this.toSql();
    }
}
