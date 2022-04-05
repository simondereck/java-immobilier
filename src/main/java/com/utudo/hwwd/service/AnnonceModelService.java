package com.utudo.hwwd.service;


import com.utudo.hwwd.models.AnnonceModel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface AnnonceModelService {

    public List<AnnonceModel> getBussinessModelList();

    public AnnonceModel findModelById(long id);

    public void save(AnnonceModel annonceModel);

    public void update(AnnonceModel annonceModel);

    public void delete(long id);

    public AnnonceModel findModelByUid(long uid);

    public BigInteger getAnnonceModelCount(String sql);

    public ArrayList<AnnonceModel> getAnnonceModelListsBySql(String sql);
}
