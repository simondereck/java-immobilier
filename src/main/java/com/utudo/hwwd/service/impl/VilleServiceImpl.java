package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.Ville;
import com.utudo.hwwd.repository.VilleRepository;
import com.utudo.hwwd.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VilleServiceImpl implements VilleService {

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private SqlRepository villeSqlRepository;

    @Override
    public List<Ville> getVilleList() {
        return villeRepository.findAll();
    }

    @Override
    public Ville findVilleById(long id) {
        return getVilleById(id);
    }

    public Ville getVilleById(long id){
        Optional<Ville> ville = villeRepository.findById(id);
        if (ville.isPresent()){
            return ville.get();
        }else
            return null;
    }

    @Override
    public void save(Ville ville) {
        String time = HwTools.getTime();
        ville.setUtime(time);
        ville.setCtime(time);
        villeRepository.save(ville);
    }

    @Override
    public void update(Ville ville) {
        ville.setUtime(HwTools.getTime());
        villeRepository.save(ville);
    }

    @Override
    public void delete(long id) {
        villeRepository.deleteById(id);
    }

    @Override
    public List<Ville> findVillesByPostcode(String postcode){
        return villeRepository.findVilleByPostcodeParamsNative(postcode);
    }

    @Override
    public List<Ville> findVilleByKeywords(String keyword){
        return villeRepository.findVilleByKeywordsParamsNative(keyword);
    }

    @Override
    public ArrayList<Object> getIdsByCode(Integer code) {
        return villeRepository.findIdsByCodeParamsNative(code);
    }

    @Override
    public Ville findOneByCode(Integer code) {
        return villeRepository.findOneByCodeParamsNative(code);
    }

    public ArrayList<Ville> getVillesByIds(ArrayList<Object> ids) {
        SqlHelper sqlHelper = new SqlHelper("hw_ville");
        sqlHelper.addInCondition("id",ids);
        return villeSqlRepository.getVillesBySql(sqlHelper.toSql());
    }

    @Override
    public ArrayList<Ville> getVillesBySql(String sql) {
        return villeSqlRepository.getVillesBySql(sql);
    }

    @Override
    public Ville getVilleByPostcode(String postcode) {
        return villeRepository.findOneByPostcodeParamsNative(postcode);
    }


}
