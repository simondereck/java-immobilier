package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.models.models.EstimateModel;
import com.utudo.hwwd.repository.EstimateModelRepository;
import com.utudo.hwwd.service.EstimateModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;

@Service
public class EstimateModelServiceImpl implements EstimateModelService {

    @Autowired
    EstimateModelRepository repository;

    @Autowired
    SqlRepository sqlRepository;

    @Override
    public void save(EstimateModel model) {
        repository.save(model);
    }

    @Override
    public void update(EstimateModel model) {
        repository.save(model);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public EstimateModel findEstimateById(long id) {
        return repository.findById(id);
    }

    @Override
    public EstimateModel findEstimateByEstId(String estId) {
        return repository.findEstimateByIdMutationParamsNative(estId);
    }

    @Override
    public ArrayList<EstimateModel> getEstimateModelBySql(String sql) {
        return sqlRepository.getEstimateModelBySql(sql);
    }

    @Override
    public BigInteger getModelCountBySql(String sql) {
        return sqlRepository.getCountBySql(sql);
    }
}
