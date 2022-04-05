package com.utudo.hwwd.service.impl;


import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.BussinessModel;
import com.utudo.hwwd.repository.BussinessModelRepository;
import com.utudo.hwwd.service.BussinessModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class BussinessModelServiceImpl implements BussinessModelService {


    @Autowired
    BussinessModelRepository bussinessModelRepository;

    @Autowired
    SqlRepository sqlRepository;

    @Override
    public List<BussinessModel> getBussinessModelList() {
        return null;
    }

    @Override
    public BussinessModel findModelById(long id) {
        return bussinessModelRepository.findById(id);
    }

    @Override
    public void save(BussinessModel model) {
        String time = HwTools.getTime();
        model.setUtime(time);
        model.setCtime(time);
        bussinessModelRepository.save(model);
    }

    @Override
    public void update(BussinessModel model) {
        String time = HwTools.getTime();
        model.setUtime(time);
        bussinessModelRepository.save(model);
    }

    @Override
    public void delete(long id) {
        bussinessModelRepository.deleteById(id);
    }

    @Override
    public BussinessModel findModelByUid(long uid) {

        return bussinessModelRepository.findByUidParamsNative(uid);
    }

    @Override
    public BigInteger getBussinessCount(String sql) {
        return sqlRepository.getCountBySql(sql);
    }

    @Override
    public ArrayList<BussinessModel> getBussinessModelListsBySql(String sql) {
        return sqlRepository.getBussinessModel(sql);
    }

    @Override
    public ArrayList<BussinessModel> getBussinessByPids(ArrayList<Object> ids) {
        SqlHelper sqlHelper = new SqlHelper(BussinessModel.class.getAnnotation(Table.class).name());
        sqlHelper.addInCondition("id",ids);
        return sqlRepository.getBussinessModel(sqlHelper.toSql());
    }
}
