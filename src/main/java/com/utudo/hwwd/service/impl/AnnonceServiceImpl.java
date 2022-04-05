package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.Annonce;
import com.utudo.hwwd.repository.AnnonceRepository;
import com.utudo.hwwd.service.AnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AnnonceServiceImpl implements AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;


    @Autowired
    private SqlRepository sqlRepository;


    @Override
    public List<Annonce> getAnnonceLists() {
        return annonceRepository.findAll();
    }

    @Override
    public List<Annonce> getAnnonceListsByStatus(Integer status,long pid) {
        return annonceRepository.findByStatusAndUidParamsNative(status,pid);

    }

    @Override
    public ArrayList<Annonce> getAnnonceListsByPid(long pid) {
        return annonceRepository.findByPidParamsNative(pid);
    }


    @Override
    public Optional<Annonce> findAnnonceById(long id) {
        return annonceRepository.findById(id);
    }



    @Override
    public void save(Annonce annonce) {
        String time = HwTools.getTime();
        annonce.setUtime(time);
        annonce.setCtime(time);
        annonce.setEnvirJson(HwTools.toJson(annonce.getEnvir()));
        annonce.setImgsJson(HwTools.toJson(annonce.getImages()));
        annonce.setPrice(annonce.getCharges()+annonce.getLoyer());
        annonce.setImmsJson(HwTools.toJson(annonce.getBase()));
        annonce.setStatus(3);
        annonceRepository.save(annonce);
    }

    @Override
    public void update(Annonce annonce) {
        String time = HwTools.getTime();
        annonce.setUtime(time);
        annonceRepository.save(annonce);
    }

    @Override
    public void delete(long id) {
        annonceRepository.deleteById(id);
    }

    @Override
    public Annonce getAnnonceByPidAndId(long id, long pid) {
        return annonceRepository.findByPidAndIdParamsNative(pid,id);
    }

    @Override
    public BigInteger getCountBySql(String sql) {
        return sqlRepository.getCountBySql(sql);
    }


    @Override
    public ArrayList<Annonce> getAnnonceByPage(String sql) {
        return sqlRepository.getAnnonces(sql);
    }

    @Override
    public ArrayList<Annonce> getAllAnnonces() {
        return (ArrayList<Annonce>) annonceRepository.findAll();
    }

    @Override
    public ArrayList<Annonce> getLastedAnnonces() {
        return annonceRepository.findLastedAnnonce();
    }


    @Override
    public HashMap<Integer, BigInteger> getAnnonceCounts() {
        HashMap<Integer,BigInteger> results = new HashMap<Integer, BigInteger>();

        SqlHelper sqlHelper = new SqlHelper("hw_annonce");
        sqlHelper.setSelect("select count(*) ");
        sqlHelper.setLimit(" ");
        BigInteger count = sqlRepository.getCountBySql(sqlHelper.toSql());
        results.put(0,count);

        SqlHelper sqlHelper1 = new SqlHelper("hw_annonce");
        sqlHelper1.setSelect("select count(*) ");
        sqlHelper1.addAndCondition("status",1);
        sqlHelper1.setLimit(" ");
        BigInteger count1 =  sqlRepository.getCountBySql(sqlHelper1.toSql());
        results.put(1,count1);

        SqlHelper sqlHelper2 = new SqlHelper("hw_annonce");
        sqlHelper2.setSelect("select count(*) ");
        sqlHelper2.addAndCondition("status",2);
        sqlHelper2.setLimit(" ");
        BigInteger count2 = sqlRepository.getCountBySql(sqlHelper2.toSql());
        results.put(2,count2);


        SqlHelper sqlHelper3 = new SqlHelper("hw_annonce");
        sqlHelper3.setSelect("select count(*) ");
        sqlHelper3.addAndCondition("status",3);
        sqlHelper3.setLimit(" ");
        BigInteger count3 = sqlRepository.getCountBySql(sqlHelper3.toSql());
        results.put(3,count3);


        return results;
    }

    @Override
    public Annonce getLastInsert(long pid) {
        return annonceRepository.findLastInsertParamsNative(pid);
    }

    @Override
    public ArrayList<Annonce> getAnnoncesByIds(ArrayList<Object> ids) {
        SqlHelper sqlHelper = new SqlHelper("hw_annonce");
        sqlHelper.addInCondition("id",ids);
        return sqlRepository.getAnnonces(sqlHelper.toSql());
    }

    @Override
    public ArrayList<Annonce> getActiveAnnoncesByLocations(ArrayList<Object> vids) {
        SqlHelper sqlHelper = new SqlHelper("hw_annonce");
        sqlHelper.addAndCondition(">","status",0);
        sqlHelper.addInCondition("location",vids);
        sqlHelper.setOrder("id","desc");
        sqlHelper.setLimit(" ");
        return sqlRepository.getAnnonces(sqlHelper.toSql());
    }


    @Override
    public Annonce findOneByPid(long pid) {
        return annonceRepository.findByUidParamsNative(pid);
    }

    @Override
    public ArrayList<Annonce> findAllActive() {
        SqlHelper sqlHelper = new SqlHelper("hw_annonce");
        sqlHelper.addAndCondition(">","status",0);
        sqlHelper.setOrder("id","desc");
        sqlHelper.setLimit(" ");
        return sqlRepository.getAnnonces(sqlHelper.toSql());
    }

    @Override
    public void deleteByUid(long uid) {
        SqlHelper sqlHelper = new SqlHelper("hw_annonce");
        annonceRepository.deleteByUidParamsNative(uid);
    }


}
