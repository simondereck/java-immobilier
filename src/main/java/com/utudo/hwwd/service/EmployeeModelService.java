package com.utudo.hwwd.service;

import com.utudo.hwwd.models.AdminLocation;
import com.utudo.hwwd.models.EmployeeModel;

import java.util.ArrayList;

public interface EmployeeModelService {

    public ArrayList<EmployeeModel> findModelsByAidAndDates(long aid, String sdate, String edate);
    public EmployeeModel findModelById(long id);

}
