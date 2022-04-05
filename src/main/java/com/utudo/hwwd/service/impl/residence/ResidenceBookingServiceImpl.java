package com.utudo.hwwd.service.impl.residence;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.residenceModel.ResidenceBooking;
import com.utudo.hwwd.repository.ResidenceBookingRepository;
import com.utudo.hwwd.service.residence.ResidenceBookingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;

@Service
public class ResidenceBookingServiceImpl implements ResidenceBookingService {


    @Resource
    SqlRepository sqlRepository;

    @Resource
    ResidenceBookingRepository repository;

    @Override
    public void save(ResidenceBooking residence) {
        String time = HwTools.getTime();
        residence.setCtime(time);
        residence.setUtime(time);
        repository.save(residence);
    }

    @Override
    public void update(ResidenceBooking residence) {
        repository.save(residence);
    }

    @Override
    public BigInteger getCountBySql(String sql) {
        return sqlRepository.getCountBySql(sql);
    }

    @Override
    public void delete(ResidenceBooking residence) {
        repository.delete(residence);
    }

    @Override
    public ArrayList<ResidenceBooking> getResidenceBookingBySql(String sql) {
        return sqlRepository.getResidencesBookingBySql(sql);
    }

    @Override
    public ResidenceBooking findOneById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public ResidenceBooking findResidenceBookingByUidAndId(long uid, long id) {
        return null;
    }


}
