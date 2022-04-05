package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.EstimationUploadModel;
import com.utudo.hwwd.repository.EstimationUploadModelRepository;
import com.utudo.hwwd.service.EstimationUploadModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.math.BigInteger;
import java.util.ArrayList;

@Service
public class EstimationUploadModelServiceImpl implements EstimationUploadModelService {

    @Autowired
    EstimationUploadModelRepository repository;

    @Autowired
    SqlRepository sqlRepository;

    @Override
    public void save(EstimationUploadModel model) {
        String time = HwTools.getTime();
        model.setUtime(time);
        repository.save(model);
    }

    @Override
    public void update(EstimationUploadModel model) {
        repository.save(model);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public EstimationUploadModel findEstimateById(long id) {
        return repository.findById(id);
    }

    @Override
    public EstimationUploadModel findEstimateByUid(long uid) {
        return repository.findEstimateByUidParamsNative(uid);
    }

    @Override
    public ArrayList<EstimationUploadModel> getEstimationUploadBySql(String sql) {
        return sqlRepository.getEstimationUploadBySql(sql);
    }

    @Override
    public BigInteger getEstimationUploadCount(String sql) {
        return sqlRepository.getCountBySql(sql);
    }

    @Override
    public EstimationUploadModel getEstimationByUidAndId(long uid, long id) {
        return repository.findModelByUidAndIdParamsNative(uid,id);
    }

    @Override
    public ArrayList<EstimationUploadModel> getEstimationEmail() {
        SqlHelper sqlHelper = new SqlHelper(EstimationUploadModel.class.getAnnotation(Table.class).name());
        sqlHelper.setLimit(" ");
        return sqlRepository.getEstimationUploadBySql(sqlHelper.toSql());
    }


}
