package com.utudo.hwwd.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utudo.hwwd.models.Annonce;
import com.utudo.hwwd.models.Needs;
import com.utudo.hwwd.models.Ville;
import com.utudo.hwwd.service.impl.NeedsServiceImpl;

import javax.xml.stream.Location;
import java.lang.reflect.Array;
import java.util.*;

public class HwMatch {

    NeedsServiceImpl needsService;

    private final ArrayList<Needs> needsPremier = new ArrayList<Needs>();
    private final ArrayList<Needs> needs = new ArrayList<Needs>();
    private final ArrayList<Needs> needsWeek = new ArrayList<Needs>();
    private final ArrayList<Needs> secondaryNeeds = new ArrayList<Needs>();
    private final HashMap<Integer,Ville> locations =  new HashMap<>();


    private Annonce annonce;


    public HwMatch(){

    }

    public void setNeedsService(NeedsServiceImpl needsService) {
        this.needsService = needsService;
    }

    public HwMatch(Annonce annonce){
        this.annonce = annonce;
        ObjectMapper objectMapper = new ObjectMapper();

        if (annonce.getImmsJson()!=null){

            HashMap<Integer,Integer> baseMap = null;
            try {
                baseMap = objectMapper.readValue(annonce.getImmsJson(), HashMap.class);
                if (baseMap!=null&&baseMap.size()>0){
                    annonce.setBase(baseMap);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        if (annonce.getEnvirJson()!=null){

            HashMap<Integer,Integer> envirMap = null;
            try {
                envirMap = objectMapper.readValue(annonce.getEnvirJson(), HashMap.class);
                if (envirMap!=null&&envirMap.size()>0){
                    annonce.setEnvir(envirMap);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<Needs> getNeeds() {
        return needs;
    }



    public void matchData(){
        matchPremier();
        HashSet<Needs> hashSet = new HashSet<>(needsPremier);
        needs.addAll(needsPremier);
        if (hashSet.size()<20){
            matchNeeds();
            hashSet.addAll(needs);
            if (hashSet.size() <20){
                matchSecondary();
                hashSet.addAll(secondaryNeeds);
                if (hashSet.size() <20){
                    matchNeedsWeek();
                    hashSet.addAll(needsWeek);
                }
            }
        }
        needs.clear();
        needs.addAll(hashSet);
        HashMap<Integer,Integer> temp = new HashMap<>();

        needs.forEach(n->{
            temp.put(n.getLocation(),n.getLocation());
        });

        getLocationVille(new ArrayList<>(temp.keySet()));

        //TODO SAVE DATA INTO DATABASE or somewhere
    }



    //for what?
    public void getLocationVille(ArrayList<Object> ids){
        SqlHelper sqlHelper = new SqlHelper("hw_ville");
        sqlHelper.addInCondition("id",ids);
    }



    public void matchPremier(){
        SqlHelper sqlHelper = new SqlHelper("hw_needs");
        sqlHelper.addAndCondition(">=","budget",this.annonce.getPrice()*0.8);
        sqlHelper.addAndCondition("<=","budget",this.annonce.getPrice()*1.2);
        sqlHelper.addAndCondition("location",this.annonce.getLocation());
        sqlHelper.addAndCondition("rtype",this.annonce.getRtype());
        sqlHelper.addAndCondition("trans",this.annonce.getTrans());

        Date date= HwTools.formatDate(this.annonce.getSdate());
        Calendar calendar = Calendar.getInstance();
        assert date != null;
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date date1 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,60);
        Date date2 = calendar.getTime();

        sqlHelper.addAndBetteweenCondition("sdate",HwTools.formatTimeToDay(date1),HwTools.formatTimeToDay(date2));

        if (annonce.getBase()!=null&&annonce.getBase().size()!=3){
            sqlHelper.addAndCondition("=","imms_json","'"+annonce.getImmsJson()+"'");
        }

        if (annonce.getEnvir()!=null && annonce.getEnvir().size()!=HwDatas.dataFunctions.size()){
            sqlHelper.addOrCondition("envir_json","'"+annonce.getEnvirJson()+"'");
        }
        ArrayList<Needs> needsList =  needsService.matchNeedsBySql(sqlHelper.toSql());
        this.needsPremier.addAll(needsList);
    }

    private void matchNeeds(){
        SqlHelper sqlHelper = new SqlHelper("hw_needs");
        sqlHelper.addAndCondition(">=","budget",this.annonce.getPrice()*0.8);
        sqlHelper.addAndCondition("<=","budget",this.annonce.getPrice()*1.2);
        sqlHelper.addAndCondition("location",this.annonce.getLocation());
        sqlHelper.addAndCondition("rtype",this.annonce.getRtype());

        Date date= HwTools.formatDate(this.annonce.getSdate());
        Calendar calendar = Calendar.getInstance();
        assert date != null;
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date date1 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,60);
        Date date2 = calendar.getTime();

        sqlHelper.addAndBetteweenCondition("sdate",HwTools.formatTimeToDay(date1),HwTools.formatTimeToDay(date2));

        if (annonce.getBase()!=null&&annonce.getBase().size()!=3){
            sqlHelper.addAndCondition("=","imms_json","'"+annonce.getImmsJson()+"'");
        }

        if (annonce.getEnvir()!=null && annonce.getEnvir().size()!=HwDatas.dataFunctions.size()){
            sqlHelper.addOrCondition("envir_json","'"+annonce.getEnvirJson()+"'");
        }

        ArrayList<Needs> needsList =  needsService.matchNeedsBySql(sqlHelper.toSql());
        this.needs.addAll(needsList);
    }

    private void matchSecondary(){

        SqlHelper sqlHelper = new SqlHelper("hw_needs");
        sqlHelper.addAndCondition(">=","budget",this.annonce.getPrice()*0.8);
        sqlHelper.addAndCondition("<=","budget",this.annonce.getPrice()*1.2);
        sqlHelper.addAndCondition("location",this.annonce.getLocation());
        sqlHelper.addAndCondition("rtype",this.annonce.getRtype());

        Date date= HwTools.formatDate(this.annonce.getSdate());
        Calendar calendar = Calendar.getInstance();
        assert date != null;
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date date1 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,60);
        Date date2 = calendar.getTime();

        sqlHelper.addAndBetteweenCondition("sdate",HwTools.formatTimeToDay(date1),HwTools.formatTimeToDay(date2));

        ArrayList<Needs> needsList =  needsService.matchNeedsBySql(sqlHelper.toSql());
        this.secondaryNeeds.addAll(needsList);


    }

    private void matchNeedsWeek(){

        SqlHelper sqlHelper = new SqlHelper("hw_needs");
        sqlHelper.addAndCondition(">=","budget",this.annonce.getPrice()*0.8);
        sqlHelper.addAndCondition("<=","budget",this.annonce.getPrice()*1.2);
        sqlHelper.addAndCondition("location",this.annonce.getLocation());
        sqlHelper.addAndCondition("rtype",this.annonce.getRtype());
        ArrayList<Needs> needsList =  needsService.matchNeedsBySql(sqlHelper.toSql());
        this.needsWeek.addAll(needsList);
    }


    @Override
    public String toString() {
        return "HwMatch{" +
                "needsService=" + needsService +
                ", needs=" + needs +
                ", needsWeek=" + needsWeek +
                ", annonce=" + annonce +
                '}';
    }
}
