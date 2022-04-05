package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.Partner;
import com.utudo.hwwd.repository.PartnerRepository;
import com.utudo.hwwd.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private SqlRepository sqlRepository;

    @Override
    public List<Partner> getPartnerList() {
        return partnerRepository.findAll();
    }

    @Override
    public Partner findPartnerById(long id) {
        return partnerRepository.findById(id);
    }

    @Override
    public void save(Partner partner) {
        String time = HwTools.getTime();
        partner.setUtime(time);
        partner.setCtime(time);
        partner.setStatus(HwDatas.PERSON_TYPE_USER_PROFILE);
        // before save
        partner.setPassword(HwTools.hashPassword(partner.getPassword()));
        partnerRepository.save(partner);
    }




    @Override
    public void update(Partner partner) {
        partner.setUtime(HwTools.getTime());
        partnerRepository.save(partner);
    }

    @Override
    public void delete(long id) {
        partnerRepository.deleteById(id);
    }

    @Override
    public Partner checkUser(String email, String telephone) {
        return new Partner();
    }

    @Override
    public Partner findUserByEmailOrTelephone(String email) {
        return partnerRepository.findByEmailOrTelephoneParamsNative(email);
    }

    @Override
    public Partner findUserByToken(String ftoken) {
        return partnerRepository.findByFtokenParamsNative(ftoken);
    }

    @Override
    public Partner findUserByEmail(String email) {
        return partnerRepository.findUserByEmailParamsNative(email);
    }

    @Override
    public Partner findUserByTelephone(String telephone) {
        return partnerRepository.findUserByTelephoneParamsNative(telephone);
    }

    @Override
    public BigInteger getPartnerCount(String sql) {
        return sqlRepository.getCountBySql(sql);
    }

    @Override
    public ArrayList<Partner> getPartnersBySql(String sql) {
        return sqlRepository.getPartnersBySql(sql);
    }

    @Override
    public ArrayList<Partner> getPartnersByIds(ArrayList<Object> ids) {
        SqlHelper sqlHelper = new SqlHelper("hw_partner");
        sqlHelper.addInCondition("id",ids);
        return sqlRepository.getPartnersBySql(sqlHelper.toSql());
    }

    @Override
    public ArrayList<Partner> getLastedPartners() {
        return partnerRepository.findLastedUsersParamsNative();
    }

    @Override
    public Partner getLastedOne() {
        return partnerRepository.getLastOne();
    }

    @Override
    public ArrayList<Partner> getPartnerEmail() {
        SqlHelper sqlHelper = new SqlHelper(Partner.class.getAnnotation(Table.class).name());
        sqlHelper.addAndCondition("status",1);
        sqlHelper.addCondition("email!=null");
        sqlHelper.setLimit(" ");
        return sqlRepository.getPartnersBySql(sqlHelper.toSql());
    }


}
