package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.AdminLocation;
import com.utudo.hwwd.repository.AdminLocationRepository;
import com.utudo.hwwd.service.AdminLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminLocationServiceImpl implements AdminLocationService {

    @Autowired
    AdminLocationRepository locationRepository;

    @Autowired
    SqlRepository sqlRepository;


    @Override
    public AdminLocation findLocationByAidAndDate(long aid, String date) {
        return locationRepository.findAdminLocationByAidAndDateParamsNative(aid,date);
    }

    @Override
    public void save(AdminLocation location) {
        String time = HwTools.getTime();
        location.setDate(HwTools.getDay());
        location.setStime(time);
        location.setUtime(time);
        locationRepository.save(location);
    }

    @Override
    public void update(AdminLocation location) {
        location.setUtime(HwTools.getTime());
        locationRepository.save(location);
    }

    @Override
    public AdminLocation findLastedLoginByAid(long aid) {
        return locationRepository.findLastedAdminLocationByAidParamsNative(aid);
    }


}
