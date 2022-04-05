package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.Annonce;
import com.utudo.hwwd.models.AnnonceBussiness;
import com.utudo.hwwd.repository.AnnonceBussinessRepository;
import com.utudo.hwwd.service.AnnonceBussinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AnnonceBussinessServiceImpl implements AnnonceBussinessService {


    @Autowired
    AnnonceBussinessRepository annonceBussinessRepository;

    @Autowired
    SqlRepository sqlRepository;

    @Override
    public void delete(AnnonceBussiness annonce) {
        annonceBussinessRepository.delete(annonce);
    }

    @Override
    public void save(AnnonceBussiness annonce) {
        String time = HwTools.getTime();
        annonce.setUtime(time);
        annonce.setCtime(time);

        annonceBussinessRepository.save(annonce);
    }

    @Override
    public void update(AnnonceBussiness annonce) {
        String time = HwTools.getTime();
        annonce.setUtime(time);

        annonceBussinessRepository.save(annonce);
    }

    @Override
    public ArrayList<AnnonceBussiness> getAnnonceBySql(String sql) {
        return sqlRepository.getAnnonceBussinessBySql(sql);
    }

    @Override
    public BigInteger getAnnonceCountBySql(String sql) {
        return sqlRepository.getCountBySql(sql);
    }

    @Override
    public AnnonceBussiness findAnnonceById(long id) {
        Optional<AnnonceBussiness> item = annonceBussinessRepository.findById(id);
        return item.orElse(null);
    }


    @Override
    public AnnonceBussiness getLastInsert(long pid) {
        return annonceBussinessRepository.findLastInsertParamsNative(pid);
    }



    @Override
    public AnnonceBussiness getAnnonceByPidAndId(long pid, long id) {
        return annonceBussinessRepository.findByPidAndIdParamsNative(pid,id);
    }


    @Override
    public ArrayList<AnnonceBussiness> getLastedAnnonces() {
        return annonceBussinessRepository.findLastedAnnonce();
    }

    @Override
    public ArrayList<AnnonceBussiness> getLastedPlatform() {
        return annonceBussinessRepository.findLastedPlatform();
    }

    @Override
    public ArrayList<AnnonceBussiness> findAllAnnonces() {
        return (ArrayList<AnnonceBussiness>) annonceBussinessRepository.findAll();
    }

    @Override
    public void deleteByUid(long uid) {
        annonceBussinessRepository.deleteByUidParamsNative(uid);
    }

    @Override
    public ArrayList<AnnonceBussiness> getAnnoncesByPid(long pid) {
        return annonceBussinessRepository.findByPidParamsNative(pid);
    }

}
