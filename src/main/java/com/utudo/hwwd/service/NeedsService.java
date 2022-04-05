package com.utudo.hwwd.service;


import com.utudo.hwwd.models.Needs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface NeedsService {
    public List<Needs> getNeedsList();

    public Optional<Needs> findNeedsById(long id);

    public void save(Needs needs);

    public void update(Needs needs);

    public void delete(long id);

    public Needs findNeedsByUid(long uid);

    public List<Needs> MatchList(Needs needs);

    public Needs findNeedsByUidAndId(long uid,long id);

    public ArrayList<Needs> matchNeedsList(Float budget,Integer location,Integer rtype,String sdate,String immsJson,String envirJson);

    public ArrayList<Needs> matchSecondaryList(Float budget,Integer location,Integer rtype,String sdate);

    public ArrayList<Needs> matchNeedsListWeek(Float budget,Integer location,Integer rtype);

    public ArrayList<Needs> matchNeedsBySql(String sql);

    public ArrayList<Needs> findAllNeddsByUid(long uid);


    public ArrayList<Needs> findNeedsByNids(ArrayList<Object> nids);

    public ArrayList<Needs> findLastedNeeds();


    public BigInteger getCountBySql(String sql);
}
