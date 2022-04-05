package com.utudo.hwwd.service.impl;


import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.DemandeBussiness;
import com.utudo.hwwd.repository.DemandeBussinessRepository;
import com.utudo.hwwd.service.DemandeBussinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class DemandeBussinessServiceImpl implements DemandeBussinessService {


    @Autowired
    SqlRepository sqlRepository;

    @Autowired
    DemandeBussinessRepository demandeBussinessRepository;

    @Override
    public List<DemandeBussiness> getDemandeBussinessList() {
        return null;
    }

    @Override
    public DemandeBussiness findDemandeById(long id) {
        return demandeBussinessRepository.findById(id);
    }

    @Override
    public void save(DemandeBussiness model) {
        String time = HwTools.getTime();
        model.setUtime(time);
        model.setCtime(time);
        model.setPid(HwTools.getCurrentUser().getId());
        model.setStatus(1);
        demandeBussinessRepository.save(model);
    }

    @Override
    public void update(DemandeBussiness model) {
        String time = HwTools.getTime();
        model.setUtime(time);
        demandeBussinessRepository.save(model);
    }

    @Override
    public void delete(long id) {
        demandeBussinessRepository.deleteById(id);
    }

    @Override
    public DemandeBussiness findDemandeByUid(long uid) {
        return demandeBussinessRepository.findByUidParamsNative(uid);
    }

    @Override
    public BigInteger getDemandeCountBySql(String sql) {
        return sqlRepository.getCountBySql(sql);
    }

    @Override
    public ArrayList<DemandeBussiness> getDemandeBySql(String sql) {
        return sqlRepository.getDemandeBussinessBySql(sql);
    }

    @Override
    public DemandeBussiness getDemandeByPidAndId(long pid, long id) {
        return demandeBussinessRepository.findByPidAndIdParamsNative(pid,id);
    }


    @Override
    public ArrayList<DemandeBussiness> getLastedDemande() {
        return demandeBussinessRepository.findLastedDemande();
    }

}
