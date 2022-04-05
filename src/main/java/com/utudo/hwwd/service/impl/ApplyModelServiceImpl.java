package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.ApplyModel;
import com.utudo.hwwd.repository.ApplyModelRepository;
import com.utudo.hwwd.service.ApplyModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ApplyModelServiceImpl implements ApplyModelService {


    @Autowired
    private ApplyModelRepository applyModelRepository;

    @Autowired
    private SqlRepository sqlRepository;

    @Override
    public void delete(ApplyModel applyModel) {
        applyModelRepository.delete(applyModel);
    }

    @Override
    public void save(ApplyModel applyModel) {
        String time = HwTools.getTime();
        applyModel.setCtime(time);
        applyModel.setUtime(time);
        applyModelRepository.save(applyModel);
    }

    @Override
    public void update(ApplyModel applyModel) {
        String time = HwTools.getTime();
        applyModel.setUtime(time);
        applyModelRepository.save(applyModel);
    }

    @Override
    public ArrayList<ApplyModel> getApplyBySql(String sql) {
        return sqlRepository.getApplyBySql(sql);
    }

    @Override
    public BigInteger getApplyCountBySql(String sql) {
        return sqlRepository.getCountBySql(sql);
    }

    @Override
    public ApplyModel findApplyById(long id) {
        Optional<ApplyModel> model = applyModelRepository.findById(id);
        if (model.isPresent()){
            return model.get();
        }
        return null;
    }

    @Override
    public ArrayList<ApplyModel> getApplyEmail() {
        SqlHelper sqlHelper = new SqlHelper(ApplyModel.class.getAnnotation(Table.class).name());
        sqlHelper.addCondition("email!=null");
        sqlHelper.setLimit(" ");
        return sqlRepository.getApplyBySql(sqlHelper.toSql());
    }


}
