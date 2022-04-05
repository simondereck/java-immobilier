package com.utudo.hwwd.service;


import com.utudo.hwwd.models.models.EstimateModel;

import java.math.BigInteger;
import java.util.ArrayList;

public interface EstimateModelService {

    public void save(EstimateModel model);

    public void update(EstimateModel model);

    public void delete(long id);


    public EstimateModel findEstimateById(long id);

    public EstimateModel findEstimateByEstId(String estId);


    public ArrayList<EstimateModel> getEstimateModelBySql(String sql);


    public BigInteger getModelCountBySql(String sql);


}
