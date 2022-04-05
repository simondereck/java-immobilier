package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.PartnerRdv;
import com.utudo.hwwd.repository.RdvRepository;
import com.utudo.hwwd.service.RdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;

@Service
public class RdvServiceImpl implements RdvService {


    @Autowired
    private RdvRepository rdvRepository;

    @Autowired
    private SqlRepository sqlRepository;

    @Override
    public void delete(PartnerRdv rdv) {
        rdvRepository.delete(rdv);
    }

    @Override
    public void save(PartnerRdv rdv) {
        String time = HwTools.getTime();
        rdv.setCtime(time);
        rdv.setUtime(time);
        rdvRepository.save(rdv);
    }

    @Override
    public void update(PartnerRdv rdv) {
        String time = HwTools.getTime();
        rdv.setUtime(time);
        rdvRepository.save(rdv);
    }

    @Override
    public ArrayList<PartnerRdv> getRdvBySql(String sql) {
        return sqlRepository.getRdvBySql(sql);
    }

    @Override
    public BigInteger getRdvCountBySql(String sql) {
        return sqlRepository.getCountBySql(sql);
    }

    @Override
    public PartnerRdv findRdvById(long id) {
        return rdvRepository.getOne(id);
    }

    @Override
    public PartnerRdv getRdvByPidAndId(long pid, long id) {
        return rdvRepository.findByPidAndIdParamsNative(pid,id);
    }
}
