package com.utudo.hwwd.service.residence;


import com.utudo.hwwd.models.residenceModel.ResidenceType;

import java.math.BigInteger;
import java.util.ArrayList;

public interface ResidenceTypeService {

    public void save(ResidenceType residence);

    public void update(ResidenceType residence);

    public BigInteger getCountBySql(String sql);

    public void delete(ResidenceType residence);

    public ArrayList<ResidenceType> getResidenceTypeBySql(String sql);


    public ResidenceType findOneById(long id);

    public ArrayList<ResidenceType> findHouseByUid(long uid);


    public ResidenceType findHouseByUidAndId(long uid,long id);
}
