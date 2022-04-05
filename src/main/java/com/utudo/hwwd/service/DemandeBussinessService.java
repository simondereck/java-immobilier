package com.utudo.hwwd.service;


import com.utudo.hwwd.models.DemandeBussiness;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface DemandeBussinessService {

    public List<DemandeBussiness> getDemandeBussinessList();

    public DemandeBussiness findDemandeById(long id);

    public void save(DemandeBussiness bussiness);

    public void update(DemandeBussiness bussiness);

    public void delete(long id);

    public DemandeBussiness findDemandeByUid(long uid);


    public BigInteger getDemandeCountBySql(String sql);


    public List<DemandeBussiness> getDemandeBySql(String sql);


    public DemandeBussiness getDemandeByPidAndId(long pid,long id);


    public ArrayList<DemandeBussiness> getLastedDemande();


}
