package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.Needs;
import com.utudo.hwwd.repository.NeedsRepository;
import com.utudo.hwwd.service.NeedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NeedsServiceImpl implements NeedsService {


    @Autowired
    private NeedsRepository needsRepository;

    @Autowired
    private SqlRepository sqlRepository;


    @Override
    public List<Needs> getNeedsList() {
        return null;
    }

    @Override
    public Optional<Needs> findNeedsById(long id) {
        return needsRepository.findById(id);
    }

    @Override
    public void save(Needs needs) {
        String time = HwTools.getTime();
        needs.setCtime(time);
        needs.setUtime(time);
        needs.setImmsJson(HwTools.toJson(needs.getBase()));
        needs.setEnvirJson(HwTools.toJson(needs.getEnvir()));
        needsRepository.save(needs);
    }


    @Override
    public void update(Needs needs) {
        needs.setUtime(HwTools.getTime());
        needsRepository.save(needs);
    }

    @Override
    public void delete(long id) {
        needsRepository.deleteById(id);
    }

    @Override
    public Needs findNeedsByUid(long uid) {
        return needsRepository.findByUidParamsNative(uid);
    }

    @Override
    public List<Needs> MatchList(Needs needs) {
        return null;
    }

    @Override
    public Needs findNeedsByUidAndId(long uid, long id) {
        return needsRepository.findByUidAndIdParamsNative(uid,id);
    }

    @Override
    public ArrayList<Needs> matchNeedsList(Float budget, Integer location, Integer rtype, String sdate, String immsJson, String envirJson) {
        return needsRepository.matchNeedsListsParamsNative(budget,location,rtype,sdate,immsJson,envirJson);
    }

    @Override
    public ArrayList<Needs> matchSecondaryList(Float budget, Integer location, Integer rtype, String sdate) {
        return needsRepository.matchNeedsListsSecondaryParamsNative(budget,location,rtype,sdate);
    }

    @Override
    public ArrayList<Needs> matchNeedsListWeek(Float budget, Integer location, Integer rtype) {
        return needsRepository.matchNeedsListsWeekParamsNative(budget,location,rtype);
    }

    @Override
    public ArrayList<Needs> matchNeedsBySql(String sql) {
        return sqlRepository.getNeedsBySql(sql);
    }

    @Override
    public ArrayList<Needs> findAllNeddsByUid(long uid) {
        return needsRepository.getNeedsByUidParamsNative(uid);
    }

    @Override
    public ArrayList<Needs> findNeedsByNids(ArrayList<Object> nids) {
        SqlHelper sqlHelper = new SqlHelper("hw_needs");
        sqlHelper.addInCondition("id",nids);
        return sqlRepository.getNeedsBySql(sqlHelper.toSql());
    }

    @Override
    public ArrayList<Needs> findLastedNeeds() {
        return needsRepository.findLastedNeeds();
    }

    @Override
    public BigInteger getCountBySql(String sql) {
        return sqlRepository.getCountBySql(sql);
    }


}
