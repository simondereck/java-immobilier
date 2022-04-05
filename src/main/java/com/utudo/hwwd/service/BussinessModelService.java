package com.utudo.hwwd.service;


import com.utudo.hwwd.models.BussinessModel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface BussinessModelService {

    public List<BussinessModel> getBussinessModelList();

    public BussinessModel findModelById(long id);

    public void save(BussinessModel bussiness);

    public void update(BussinessModel bussiness);

    public void delete(long id);

    public BussinessModel findModelByUid(long uid);

    public BigInteger getBussinessCount(String sql);

    public ArrayList<BussinessModel> getBussinessModelListsBySql(String sql);

    public ArrayList<BussinessModel> getBussinessByPids(ArrayList<Object> ids);
}
