package com.utudo.hwwd.service.impl.residence;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.residenceModel.Residence;
import com.utudo.hwwd.repository.ResidenceRepository;
import com.utudo.hwwd.service.residence.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ResidenceServiceImpl implements ResidenceService {

    @Autowired
    ResidenceRepository repository;

    @Autowired
    SqlRepository sqlRepository;

    @Override
    public void save(Residence residence) {
        String time = HwTools.getTime();
        residence.setCtime(time);
        residence.setUtime(time);
        repository.save(residence);

    }

    @Override
    public void update(Residence residence) {

    }

    @Override
    public BigInteger getCountBySql(String sql) {
        return sqlRepository.getCountBySql(sql);
    }

    @Override
    public void delete(Residence residence) {
        repository.delete(residence);
    }

    @Override
    public ArrayList<Residence> getResidenceBySql(String sql) {
        return sqlRepository.getResidencesBySql(sql);
    }

    @Override
    public Residence findOneById(long id) {
        Optional<Residence> res = repository.findById(id);
        return res.orElse(null);
    }

    @Override
    public ArrayList<Residence> findResidenceByUid(long uid) {
        return null;
    }

    @Override
    public Residence findResidenceByUidAndId(long uid, long id) {
        return repository.findResidenceByIdAndUidParamsNative(id,uid);
    }

    @Override
    public ArrayList<Residence> findLastedResidences() {
        SqlHelper sqlHelper = new SqlHelper(Residence.class.getAnnotation(Table.class).name());
        sqlHelper.setOrder("id","desc");
        sqlHelper.setLimit("limit 4");
        return sqlRepository.getResidencesBySql(sqlHelper.toSql());
    }



}
