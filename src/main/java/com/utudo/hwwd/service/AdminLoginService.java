package com.utudo.hwwd.service;

import com.utudo.hwwd.models.AdminLocation;
import com.utudo.hwwd.models.AdminLogin;

import java.util.ArrayList;

public interface AdminLoginService {

    public ArrayList<AdminLogin> findLoginsByUidAndDate(long uid, String date);


    public void save(AdminLogin login);


    public void update(AdminLogin login);


}
