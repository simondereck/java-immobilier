package com.utudo.hwwd.service;


import com.utudo.hwwd.models.EstimationUploadModel;

import java.math.BigInteger;
import java.util.ArrayList;

public interface EstimationUploadModelService {

    public void save(EstimationUploadModel model);

    public void update(EstimationUploadModel model);

    public void delete(long id);


    public EstimationUploadModel findEstimateById(long id);

    public EstimationUploadModel findEstimateByUid(long uid);


    public ArrayList<EstimationUploadModel> getEstimationUploadBySql(String sql);


    public BigInteger getEstimationUploadCount(String sql);


    public EstimationUploadModel getEstimationByUidAndId(long uid , long id);

    public ArrayList<EstimationUploadModel> getEstimationEmail();
}
