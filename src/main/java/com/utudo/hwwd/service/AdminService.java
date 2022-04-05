package com.utudo.hwwd.service;

import com.utudo.hwwd.models.Admin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface AdminService {
    public ArrayList<Admin> getAdminLists();

    public Admin findAdminById(long id);

    public void save(Admin admin);

    public void update(Admin admin);

    public void delete(long id);

    public Admin checkAdmin(String email, String telephone);


    public Admin findAdminByEmailOrTelephone(String email);

    public ArrayList<Admin> findAdminByStatus(int status);

    public Admin findAdminByToken(String token);

    public BigInteger getCountBySql(String sql);

    public ArrayList<Admin> getAdminsBySql(String sql);

}
