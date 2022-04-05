package com.utudo.hwwd.service;
import com.utudo.hwwd.models.CobberShop;
import com.utudo.hwwd.models.cobber.PowerModel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface OrderPowerService {
    public List<PowerModel> getOrderList();

    public PowerModel findOrderById(long id);

    public void save(PowerModel model);

    public void update(PowerModel model);

    public void delete(long id);

    public BigInteger getOrderCount(String sql);

    public ArrayList<PowerModel> getOrdersBySql(String sql);

    public PowerModel findOrderByUid(long uid);
}
