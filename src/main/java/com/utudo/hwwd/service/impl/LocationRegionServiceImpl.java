package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.LocationRegion;
import com.utudo.hwwd.repository.LocationRegionRepository;
import com.utudo.hwwd.service.LocationRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationRegionServiceImpl implements LocationRegionService {


    @Autowired
    LocationRegionRepository locationRegionRepository;

    @Override
    public void save(LocationRegion region) {
        String time = HwTools.getTime();
        region.setCtime(time);
        region.setUtime(time);
        locationRegionRepository.save(region);
    }

    @Override
    public void update(LocationRegion region) {
        String time = HwTools.getTime();
        region.setUtime(time);
        locationRegionRepository.save(region);
    }

    @Override
    public LocationRegion findOneByCode(long code) {
        return locationRegionRepository.findLocactionByCodeParamsNative(code);
    }
}
