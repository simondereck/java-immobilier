package com.utudo.hwwd.service;
import com.utudo.hwwd.models.Partner;
import com.utudo.hwwd.models.PartnerRdv;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface PartnerService {
    public List<Partner> getPartnerList();

    public Partner findPartnerById(long id);

    public void save(Partner partner);

    public void update(Partner partner);

    public void delete(long id);

    public Partner checkUser(String email, String telephone);


    public Partner findUserByEmailOrTelephone(String email);

    public Partner findUserByToken(String ftoken);

    public Partner findUserByEmail(String email);

    public Partner findUserByTelephone(String telephone);


    public BigInteger getPartnerCount(String sql);

    public ArrayList<Partner> getPartnersBySql(String sql);

    public ArrayList<Partner> getPartnersByIds(ArrayList<Object> ids);

    public ArrayList<Partner> getLastedPartners();

    public Partner getLastedOne();

    public ArrayList<Partner> getPartnerEmail();


}
