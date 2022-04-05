package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.Cobber;
import com.utudo.hwwd.repository.CobberRepository;
import com.utudo.hwwd.service.CobberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class CobberServiceImpl implements CobberService {

    @Autowired
    private CobberRepository cobberRepository;

    @Autowired
    private SqlRepository sqlRepository;

    @Override
    public List<Cobber> getCobberList() {
        return cobberRepository.findAll();
    }

    @Override
    public Cobber findCobberById(long id) {
        return cobberRepository.findById(id);
    }

    @Override
    public void save(Cobber cobber) {
        String time = HwTools.getTime();
        cobber.setUtime(time);
        cobber.setCtime(time);
        cobber.setStatus(HwDatas.PERSON_TYPE_USER_PROFILE);
        // before save
        cobber.setPassword(HwTools.hashPassword(cobber.getPassword()));
        cobberRepository.save(cobber);
    }

    @Override
    public void emailSave(Cobber cobber) {
        String time = HwTools.getTime();
        cobber.setUtime(time);
        cobber.setCtime(time);
        cobber.setStatus(HwDatas.PERSON_TYPE_USER_EMAIL);
        // before save
        cobber.setPassword(HwTools.hashPassword(cobber.getPassword()));
        cobberRepository.save(cobber);
    }


    @Override
    public void update(Cobber cobber) {
        cobber.setUtime(HwTools.getTime());
        cobberRepository.save(cobber);
    }

    @Override
    public void delete(long id) {
        cobberRepository.deleteById(id);
    }

    @Override
    public Cobber checkCobber(String email, String telephone) {
        return new Cobber();
    }

    @Override
    public Cobber findCobberByEmailOrTelephone(String email) {
        return cobberRepository.findByEmailOrTelephoneParamsNative(email);
    }

    @Override
    public Cobber findCobberByToken(String ftoken) {
        return cobberRepository.findByFtokenParamsNative(ftoken);
    }

    @Override
    public Cobber findCobberByEmail(String email) {
        return cobberRepository.findCobberByEmailParamsNative(email);
    }

    @Override
    public Cobber findCobberByTelephone(String telephone) {
        return cobberRepository.findCobberByTelephoneParamsNative(telephone);
    }

    @Override
    public BigInteger getCobberCount(String sql) {
        return sqlRepository.getCountBySql(sql);
    }

    @Override
    public ArrayList<Cobber> getCobbersBySql(String sql) {
        return sqlRepository.getCobbersBySql(sql);
    }

    @Override
    public ArrayList<Cobber> getCobbersByIds(ArrayList<Object> ids) {
        SqlHelper sqlHelper = new SqlHelper("hw_cobber");
        sqlHelper.addInCondition("id",ids);
        return sqlRepository.getCobbersBySql(sqlHelper.toSql());
    }

    @Override
    public ArrayList<Cobber> getLastedCobbers() {
        return cobberRepository.findLastedUsersParamsNative();
    }

    @Override
    public ArrayList<Cobber> getCobberEmail() {
        SqlHelper sqlHelper = new SqlHelper(Cobber.class.getAnnotation(Table.class).name());
        sqlHelper.addCondition("email!=null");
        sqlHelper.setLimit(" ");
        return sqlRepository.getCobbersBySql(sqlHelper.toSql());
    }


}
