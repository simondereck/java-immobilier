package com.utudo.hwwd.service;

import com.utudo.hwwd.models.Ville;

import java.util.ArrayList;
import java.util.List;

public interface VilleService {

    public List<Ville> getVilleList();

    public Ville findVilleById(long id);

    public void save(Ville ville);

    public void update(Ville ville);

    public void delete(long id);


    public List<Ville> findVillesByPostcode(String postcode);

    public List<Ville> findVilleByKeywords(String keyword);


    public ArrayList<Object> getIdsByCode(Integer code);


    public Ville findOneByCode(Integer code);

    public ArrayList<Ville> getVillesByIds(ArrayList<Object> ids);

    public ArrayList<Ville> getVillesBySql(String sql);

    public Ville getVilleByPostcode(String postcode);
}
