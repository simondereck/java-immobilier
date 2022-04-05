package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.Garantie;
import com.utudo.hwwd.repository.GarantieRepository;
import com.utudo.hwwd.service.GarantieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GarantieServiceImpl implements GarantieService {

    @Autowired
    private GarantieRepository garantieRepository;

    @Override
    public Optional<Garantie> findGarantieById(long id) {
        return Optional.empty();
    }

    @Override
    public void save(Garantie garantie) {
        String time = HwTools.getTime();
        garantie.setCtime(time);
        garantie.setUtime(time);
        garantieRepository.save(garantie);
    }

    @Override
    public void update(Garantie garantie) {
        String time = HwTools.getTime();
        garantie.setUtime(time);
        garantieRepository.save(garantie);
    }

    @Override
    public void delete(long id) {
        garantieRepository.deleteById(id);
    }

    @Override
    public Garantie findGarantieByUid(long uid) {
        return garantieRepository.findByUidParamsNative(uid);
    }

    @Override
    public Optional<Garantie> findGarantieByNidAndUid(long nid, long uid) {
        return garantieRepository.findGarantieByUidAndNidParamsNative(nid, uid);
    }

    @Override
    public Garantie findGarantieByIdAndUid(long id, long uid) {
        return garantieRepository.findGarantieByIdAndUidParamsNative(id,uid);
    }
}
