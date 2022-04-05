package com.utudo.hwwd.service;

import com.utudo.hwwd.models.Universite;

import java.util.ArrayList;

public interface UniversiteService {

    public void save(Universite universite);

    public void update(Universite universite);

    public ArrayList<Universite> getUniversiteBySql(String sql);


    public Universite getById(long id);
}
