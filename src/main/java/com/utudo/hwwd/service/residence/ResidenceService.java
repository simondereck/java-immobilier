package com.utudo.hwwd.service.residence;


import com.utudo.hwwd.models.residenceModel.Residence;

import java.math.BigInteger;
import java.util.ArrayList;

public interface ResidenceService {

    public void save(Residence residence);

    public void update(Residence residence);

    public BigInteger getCountBySql(String sql);

    public void delete(Residence residence);

    public ArrayList<Residence> getResidenceBySql(String sql);


    public Residence findOneById(long id);

    public ArrayList<Residence> findResidenceByUid(long uid);


    public Residence findResidenceByUidAndId(long uid,long id);


    public ArrayList<Residence> findLastedResidences();
}
