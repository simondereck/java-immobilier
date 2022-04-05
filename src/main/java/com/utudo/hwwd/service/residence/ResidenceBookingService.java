package com.utudo.hwwd.service.residence;


import com.utudo.hwwd.models.residenceModel.ResidenceBooking;

import java.math.BigInteger;
import java.util.ArrayList;

public interface ResidenceBookingService {

    public void save(ResidenceBooking residence);

    public void update(ResidenceBooking residence);

    public BigInteger getCountBySql(String sql);

    public void delete(ResidenceBooking residence);

    public ArrayList<ResidenceBooking> getResidenceBookingBySql(String sql);


    public ResidenceBooking findOneById(long id);


    public ResidenceBooking findResidenceBookingByUidAndId(long uid,long id);


}
