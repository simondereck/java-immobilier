package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.AnnonceLatLng;
import com.utudo.hwwd.repository.AnnonceLatLngRepository;
import com.utudo.hwwd.service.AnnonceLatLngService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AnnonceLatLngServiceImpl implements AnnonceLatLngService {

    @Autowired
    AnnonceLatLngRepository annonceLatLngRepository;

    @Autowired
    SqlRepository sqlRepository;

    @Override
    public void save(AnnonceLatLng latLng) {
        String time = HwTools.getTime();
        latLng.setUtime(time);
        latLng.setCtime(time);
        latLng.setBboxJson(HwTools.toJson(latLng.getBbox()));
        latLng.setCenterJson(HwTools.toJson(latLng.getCenter()));
        latLng.setGeometryJson(HwTools.toJson(latLng.getGeometry()));
        annonceLatLngRepository.save(latLng);
    }

    @Override
    public void update(AnnonceLatLng latLng) {
        String time = HwTools.getTime();
        latLng.setUtime(time);
        annonceLatLngRepository.save(latLng);
    }

    public Optional<AnnonceLatLng> findOneById(long id) {
        return annonceLatLngRepository.findById(id);
    }

    @Override
    public AnnonceLatLng findOnceByPidAndAid(long pid, long aid) {
        return annonceLatLngRepository.findByPidAndAidParamsNative(pid,aid);
    }

    @Override
    public AnnonceLatLng findOnceByPidAndBid(long pid, long bid) {
        return annonceLatLngRepository.findByPidAndBidParamsNative(pid,bid);
    }


    @Override
    public ArrayList<AnnonceLatLng> findAnnonceLatLngBySql(String sql) {
        return sqlRepository.getAnnonceLatLngBySql(sql);
    }

    @Override
    public ArrayList<AnnonceLatLng> getAnnonceLatLngByIds(ArrayList<Object> ids) {
        SqlHelper sqlHelper = new SqlHelper("hw_annonce_lat_lng");
        sqlHelper.addInCondition("id",ids);
        return sqlRepository.getAnnonceLatLngBySql(sqlHelper.toSql());
    }
}
