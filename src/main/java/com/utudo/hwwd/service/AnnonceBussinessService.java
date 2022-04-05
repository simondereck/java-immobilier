package com.utudo.hwwd.service;


import com.utudo.hwwd.models.AnnonceBussiness;

import java.math.BigInteger;
import java.util.ArrayList;

public interface AnnonceBussinessService {
    public void delete(AnnonceBussiness annonce);
    public void save(AnnonceBussiness annonce);

    public void update(AnnonceBussiness annonce);

    public ArrayList<AnnonceBussiness> getAnnonceBySql(String sql);

    public BigInteger getAnnonceCountBySql(String sql);

    public AnnonceBussiness findAnnonceById(long id);

    public AnnonceBussiness getAnnonceByPidAndId(long pid,long id);

    public AnnonceBussiness getLastInsert(long pid);

    public ArrayList<AnnonceBussiness> getLastedAnnonces();

    public ArrayList<AnnonceBussiness> getLastedPlatform();

    public ArrayList<AnnonceBussiness> findAllAnnonces();

    public void deleteByUid(long uid);

    public ArrayList<AnnonceBussiness> getAnnoncesByPid(long pid);

}
