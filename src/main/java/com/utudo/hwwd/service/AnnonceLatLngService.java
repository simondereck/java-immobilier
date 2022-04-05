package com.utudo.hwwd.service;

import com.utudo.hwwd.models.Annonce;
import com.utudo.hwwd.models.AnnonceLatLng;
import com.utudo.hwwd.models.Ville;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AnnonceLatLngService {

    public void save(AnnonceLatLng latLng);

    public void update(AnnonceLatLng latLng);

    public Optional<AnnonceLatLng> findOneById(long id);


    public AnnonceLatLng findOnceByPidAndAid(long pid,long aid);


    public AnnonceLatLng findOnceByPidAndBid(long pid,long bid);


    public ArrayList<AnnonceLatLng> findAnnonceLatLngBySql(String sql);


    public ArrayList<AnnonceLatLng> getAnnonceLatLngByIds(ArrayList<Object> ids);
}
