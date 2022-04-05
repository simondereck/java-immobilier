package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.Pays;
import com.utudo.hwwd.repository.PaysRepository;
import com.utudo.hwwd.service.PaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaysServiceImpl implements PaysService {

    @Autowired
    PaysRepository paysRepository;

    @Autowired
    SqlRepository sqlRepository;


    @Override
    public List<Pays> getPaysLists() {
        SqlHelper sqlHelper = new SqlHelper("hw_pays");
        sqlHelper.setLimit(" ");
        sqlHelper.setOrder("premission","desc");
        return sqlRepository.getPaysBySql(sqlHelper.toSql());
    }

    @Override
    public Pays findPayById(long id) {
        return paysRepository.getOne(id);
    }

    public Pays getPayById(long id){
        Optional<Pays> pays = paysRepository.findById(id);
        if (pays.isPresent()){
            return pays.get();
        }
        return null;
    }

    @Override
    public void save(Pays pays) {
        String time = HwTools.getTime();
        pays.setCtime(time);
        pays.setUtime(time);
        paysRepository.save(pays);
    }

    @Override
    public void update(Pays pays) {
        String time = HwTools.getTime();
        pays.setUtime(time);
        paysRepository.save(pays);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public ArrayList<Pays> getPaysByIds(ArrayList<Object> ids) {
        SqlHelper sqlHelper = new SqlHelper("hw_pays");
        sqlHelper.addInCondition("id",ids);
        return sqlRepository.getPaysBySql(sqlHelper.toSql());
    }

    @Override
    public BigInteger getPaysCount(String sql) {
        return sqlRepository.getCountBySql(sql);
    }

    @Override
    public ArrayList<Pays> getPaysBySql(String sql) {
        return sqlRepository.getPaysBySql(sql);
    }
}
