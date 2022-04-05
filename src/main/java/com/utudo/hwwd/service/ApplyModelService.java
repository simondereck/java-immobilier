package com.utudo.hwwd.service;


import com.utudo.hwwd.models.ApplyModel;

import java.math.BigInteger;
import java.util.ArrayList;

public interface ApplyModelService {
    public void delete(ApplyModel apply);
    public void save(ApplyModel apply);

    public void update(ApplyModel apply);

    public ArrayList<ApplyModel> getApplyBySql(String sql);

    public BigInteger getApplyCountBySql(String sql);

    public ApplyModel findApplyById(long id);

    public ArrayList<ApplyModel> getApplyEmail();

}
