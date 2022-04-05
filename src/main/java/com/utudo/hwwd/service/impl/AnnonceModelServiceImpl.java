package com.utudo.hwwd.service.impl;


import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.AnnonceModel;
import com.utudo.hwwd.repository.AnnonceModelRepository;
import com.utudo.hwwd.service.AnnonceModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnnonceModelServiceImpl implements AnnonceModelService {


    @Autowired
    AnnonceModelRepository annonceModelRepository;

    @Autowired
    SqlRepository sqlRepository;

    @Override
    public List<AnnonceModel> getBussinessModelList() {
        return null;
    }

    @Override
    public AnnonceModel findModelById(long id) {
        return annonceModelRepository.findById(id);
    }

    @Override
    public void save(AnnonceModel model) {
        String time = HwTools.getTime();
        model.setUtime(time);
        model.setCtime(time);
        annonceModelRepository.save(model);
    }

    @Override
    public void update(AnnonceModel model) {
        String time = HwTools.getTime();
        model.setUtime(time);
        annonceModelRepository.save(model);
    }

    @Override
    public void delete(long id) {
        annonceModelRepository.deleteById(id);
    }

    @Override
    public AnnonceModel findModelByUid(long uid) {
        return annonceModelRepository.findByUidParamsNative(uid);
    }

    @Override
    public BigInteger getAnnonceModelCount(String sql) {
        return sqlRepository.getCountBySql(sql);
    }

    @Override
    public ArrayList<AnnonceModel> getAnnonceModelListsBySql(String sql) {
        return sqlRepository.getAnnonceModelListsBySql(sql);
    }
}
