package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.CobberShop;
import com.utudo.hwwd.repository.CobberShopRepository;
import com.utudo.hwwd.service.CobberShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class CobberShopServiceImpl implements CobberShopService {

    @Autowired
    CobberShopRepository shopRepository;

    @Autowired
    SqlRepository sqlRepository;

    @Override
    public List<CobberShop> getCobberList() {
        return null;
    }

    @Override
    public CobberShop findCobberById(long id) {
        return shopRepository.findById(id);
    }

    @Override
    public void save(CobberShop cobber) {
        String time = HwTools.getTime();
        cobber.setStatus(HwDatas.PERSON_TYPE_USER_BLOCK);
        cobber.setUtime(time);
        cobber.setCtime(time);
        shopRepository.save(cobber);
    }

    @Override
    public void update(CobberShop cobber) {
        String time = HwTools.getTime();
        cobber.setUtime(time);
        shopRepository.save(cobber);
    }

    @Override
    public void delete(long id) {
        shopRepository.deleteById(id);
    }

    @Override
    public BigInteger getCobberShopCount(String sql) {
        return null;
    }

    @Override
    public ArrayList<CobberShop> getCobbersShopBySql(String sql) {
        return sqlRepository.getCobberShopsBySql(sql);
    }

    @Override
    public CobberShop findCobberByUid(long uid) {
        return shopRepository.findByUidParamsNative(uid);
    }
}
