package com.utudo.hwwd.service.impl.residence;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.residenceModel.ResidenceBooking;
import com.utudo.hwwd.models.residenceModel.ResidenceBookingRoom;
import com.utudo.hwwd.repository.ResidenceBookingRoomRepository;
import com.utudo.hwwd.service.residence.ResidenceBookingRoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;

@Service
public class ResidenceBookingRoomServiceImpl implements ResidenceBookingRoomService {


    @Resource
    SqlRepository sqlRepository;

    @Resource
    ResidenceBookingRoomRepository repository;

    @Override
    public void save(ResidenceBookingRoom residence) {
        String time = HwTools.getTime();
        residence.setCtime(time);
        residence.setUtime(time);
        repository.save(residence);
    }

    @Override
    public void update(ResidenceBookingRoom residence) {
        String time = HwTools.getTime();
        residence.setUtime(time);
        repository.save(residence);
    }

    @Override
    public BigInteger getCountBySql(String sql) {
        return sqlRepository.getCountBySql(sql);
    }

    @Override
    public void delete(ResidenceBookingRoom residence) {
        repository.delete(residence);
    }

    @Override
    public ArrayList<ResidenceBookingRoom> getResidenceBookingBySql(String sql) {
        return sqlRepository.getResidencesBookingRoomBySql(sql);
    }

    @Override
    public ResidenceBookingRoom findOneById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public ResidenceBookingRoom findResidenceBookingRoomByUidAndId(long uid, long id) {
        return repository.findResidenceBookingRoomByIdAndUidParamsNative(id,uid);
    }

    @Override
    public ResidenceBookingRoom findeResidenceBookingRoomByUid(long uid) {
        return repository.findResidenceBookingRoomByUidParamsNative(uid);
    }
}
