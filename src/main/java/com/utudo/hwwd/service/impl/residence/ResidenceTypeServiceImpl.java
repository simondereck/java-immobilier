package com.utudo.hwwd.service.impl.residence;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.residenceModel.ResidenceType;
import com.utudo.hwwd.repository.ResidenceTypeRepository;
import com.utudo.hwwd.service.residence.ResidenceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ResidenceTypeServiceImpl implements ResidenceTypeService {

    @Autowired
    ResidenceTypeRepository repository;

    @Autowired
    SqlRepository sqlRepository;

    @Override
    public void save(ResidenceType residence) {
        String time = HwTools.getTime();
        residence.setCtime(time);
        residence.setUtime(time);
        repository.save(residence);
    }

    @Override
    public void update(ResidenceType residence) {
        repository.save(residence);

    }

    @Override
    public BigInteger getCountBySql(String sql) {
        return sqlRepository.getCountBySql(sql);
    }


    @Override
    public void delete(ResidenceType residence) {
        repository.delete(residence);
    }


    @Override
    public ArrayList<ResidenceType> getResidenceTypeBySql(String sql) {
        return sqlRepository.getResidencesTypeBySql(sql);
    }

    @Override
    public ResidenceType findOneById(long id) {
        Optional<ResidenceType> item = repository.findById(id);
        return item.orElse(null);
    }


    @Override
    public ArrayList<ResidenceType> findHouseByUid(long uid) {
        return null;
    }

    @Override
    public ResidenceType findHouseByUidAndId(long uid, long id) {
        return repository.findResidenceByIdAndUidParamsNative(id,uid);
    }


}
