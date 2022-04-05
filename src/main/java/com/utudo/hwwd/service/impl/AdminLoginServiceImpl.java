package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.AdminLogin;
import com.utudo.hwwd.repository.AdminLoginRepository;
import com.utudo.hwwd.service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    AdminLoginRepository loginRepository;


    @Override
    public ArrayList<AdminLogin> findLoginsByUidAndDate(long uid, String date) {
        return loginRepository.findLoginsByUidAndDateParamsNative(uid,date);
    }

    @Override
    public void save(AdminLogin login) {
        login.setCtime(HwTools.getTime());
        loginRepository.save(login);
    }

    @Override
    public void update(AdminLogin login) {

    }



}
