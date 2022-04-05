package com.utudo.hwwd.service;


import com.utudo.hwwd.models.PartnerRdv;

import java.math.BigInteger;
import java.util.ArrayList;

public interface RdvService {
    public void delete(PartnerRdv rdv);
    public void save(PartnerRdv rdv);

    public void update(PartnerRdv rdv);

    public ArrayList<PartnerRdv> getRdvBySql(String sql);

    public BigInteger getRdvCountBySql(String sql);

    public PartnerRdv findRdvById(long id);

    public PartnerRdv getRdvByPidAndId(long pid,long id);
}
