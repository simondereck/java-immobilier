package com.utudo.hwwd.service.residence;


import com.utudo.hwwd.models.residenceModel.ResidenceBooking;
import com.utudo.hwwd.models.residenceModel.ResidenceBookingRoom;

import java.math.BigInteger;
import java.util.ArrayList;

public interface ResidenceBookingRoomService {

    public void save(ResidenceBookingRoom residence);

    public void update(ResidenceBookingRoom residence);

    public BigInteger getCountBySql(String sql);

    public void delete(ResidenceBookingRoom residence);

    public ArrayList<ResidenceBookingRoom> getResidenceBookingBySql(String sql);


    public ResidenceBookingRoom findOneById(long id);


    public ResidenceBookingRoom findResidenceBookingRoomByUidAndId(long uid,long id);


    public ResidenceBookingRoom findeResidenceBookingRoomByUid(long uid);

}
