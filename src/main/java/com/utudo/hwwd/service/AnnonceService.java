package com.utudo.hwwd.service;

import com.utudo.hwwd.models.Annonce;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface AnnonceService {
    public List<Annonce> getAnnonceLists();

    public List<Annonce> getAnnonceListsByStatus(Integer status,long uid);

    public List<Annonce> getAnnonceListsByPid(long pid);

    public Optional<Annonce> findAnnonceById(long id);

    public void save(Annonce annonce);

    public void update(Annonce annonce);

    public void delete(long id);

    public Annonce getAnnonceByPidAndId(long id,long pid);

    public BigInteger getCountBySql(String sql);

    public ArrayList<Annonce> getAnnonceByPage(String sql);

    public ArrayList<Annonce> getAllAnnonces();

    public ArrayList<Annonce> getLastedAnnonces();

    public HashMap<Integer, BigInteger> getAnnonceCounts();

    public Annonce getLastInsert(long pid);

    public ArrayList<Annonce> getAnnoncesByIds(ArrayList<Object> ids);

    public ArrayList<Annonce> getActiveAnnoncesByLocations(ArrayList<Object> vids);

    public Annonce findOneByPid(long pid);

    public ArrayList<Annonce> findAllActive();

    public void deleteByUid(long uid);
}
