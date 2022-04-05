package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.Universite;
import com.utudo.hwwd.repository.UniversiteRepository;
import com.utudo.hwwd.service.UniversiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UniversiteServiceImpl implements UniversiteService {


    @Autowired
    UniversiteRepository universiteRepository;

    @Autowired
    SqlRepository sqlRepository;

    @Override
    public void save(Universite universite) {
        String time = HwTools.getTime();
        universite.setUtime(time);
        universite.setCtime(time);
        universiteRepository.save(universite);
    }

    @Override
    public void update(Universite universite) {
        String time = HwTools.getTime();
        universite.setUtime(time);
        universiteRepository.save(universite);
    }

    @Override
    public ArrayList<Universite> getUniversiteBySql(String sql) {
        return sqlRepository.getUniversiteBySql(sql);
    }

    @Override
    public Universite getById(long id) {
        return universiteRepository.findById(id);
    }
}
