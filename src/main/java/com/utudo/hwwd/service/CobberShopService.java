package com.utudo.hwwd.service;
import com.utudo.hwwd.models.CobberShop;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface CobberShopService {
    public List<CobberShop> getCobberList();

    public CobberShop findCobberById(long id);

    public void save(CobberShop cobber);

    public void update(CobberShop cobber);

    public void delete(long id);

    public BigInteger getCobberShopCount(String sql);

    public ArrayList<CobberShop> getCobbersShopBySql(String sql);

    public CobberShop findCobberByUid(long uid);
}
