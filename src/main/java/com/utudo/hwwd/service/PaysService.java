package com.utudo.hwwd.service;


import com.utudo.hwwd.models.Pays;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface PaysService {

    public List<Pays> getPaysLists();

    public Pays findPayById(long id);

    public void save(Pays pays);

    public void update(Pays pays);

    public void delete(long id);

    public ArrayList<Pays> getPaysByIds(ArrayList<Object> ids);


    public BigInteger getPaysCount(String sql);


    public ArrayList<Pays> getPaysBySql(String sql);
}
