package com.utudo.hwwd.service;

import com.utudo.hwwd.models.AdminLocation;

public interface AdminLocationService {

    public AdminLocation findLocationByAidAndDate(long aid,String date);


    public void save(AdminLocation location);


    public void update(AdminLocation location);


    public AdminLocation findLastedLoginByAid(long aid);

}
