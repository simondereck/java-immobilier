package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.Admin;
import com.utudo.hwwd.repository.AdminRepository;
import com.utudo.hwwd.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private SqlRepository sqlRepository;

    @Override
    public ArrayList<Admin> getAdminLists() {
        return (ArrayList<Admin>) adminRepository.findAll();
    }

    @Override
    public Admin findAdminById(long id) {
        return adminRepository.findById(id);
    }

    @Override
    public void save(Admin admin) {
        String time = HwTools.getTime();
        admin.setCtime(time);
        admin.setUtime(time);
        admin.setPassword(HwTools.hashPassword(admin.getPassword()));
        admin.setAstatus(HwDatas.PERSON_TYPE_ADMIN_STATUS_ACTIVE);

        adminRepository.save(admin);
    }

    @Override
    public void update(Admin admin) {
        String time = HwTools.getTime();
        admin.setUtime(time);
        adminRepository.save(admin);
    }

    @Override
    public void delete(long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public Admin checkAdmin(String email, String telephone) {
        return null;
    }

    @Override
    public Admin findAdminByEmailOrTelephone(String email) {
        return adminRepository.findByEmailOrTelephoneParamsNative(email);
    }

    @Override
    public ArrayList<Admin> findAdminByStatus(int status) {
        return adminRepository.findAdminByStatusParamsNative(status);
    }

    @Override
    public Admin findAdminByToken(String token) {
        return adminRepository.findAdminByTokenParamsNative(token);
    }

    @Override
    public BigInteger getCountBySql(String sql) {
        return sqlRepository.getCountBySql(sql);
    }

    @Override
    public ArrayList<Admin> getAdminsBySql(String sql) {
        return sqlRepository.getAdminsBySql(sql);
    }


}
