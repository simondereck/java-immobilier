package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.EmployeeModel;
import com.utudo.hwwd.repository.EmployeeModelRespository;
import com.utudo.hwwd.service.EmployeeModelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Table;
import java.util.ArrayList;

@Service
public class EmployeeModelServiceImpl implements EmployeeModelService {

    @Resource
    EmployeeModelRespository modelRespository;

    @Resource
    SqlRepository sqlRepository;

    @Override
    public ArrayList<EmployeeModel> findModelsByAidAndDates(long aid, String sdate, String edate) {
        SqlHelper sqlHelper = new SqlHelper(EmployeeModel.class.getAnnotation(Table.class).name());
        sqlHelper.addAndBetteweenCondition("date",sdate,edate);
        sqlHelper.addAndCondition("uid",aid);
        sqlHelper.setLimit("");
        return sqlRepository.getEmployeeModels(sqlHelper.toSql());
    }

    @Override
    public EmployeeModel findModelById(long id) {
        return modelRespository.findById(id);
    }
}
