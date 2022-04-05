package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.cobber.PowerModel;
import com.utudo.hwwd.repository.OrderPowerRepository;
import com.utudo.hwwd.service.OrderPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderPowerServiceImpl implements OrderPowerService {

    @Autowired
    OrderPowerRepository orderPowerRepository;

    @Override
    public List<PowerModel> getOrderList() {
        return orderPowerRepository.findAll();
    }

    @Override
    public PowerModel findOrderById(long id) {
        return orderPowerRepository.findById(id);
    }

    @Override
    public void save(PowerModel model) {
        String time = HwTools.getTime();
        model.setCtime(time);
        model.setUtime(time);
        orderPowerRepository.save(model);
    }

    @Override
    public void update(PowerModel model) {
        String time = HwTools.getTime();
        model.setUtime(time);
        orderPowerRepository.save(model);
    }

    @Override
    public void delete(long id) {
        orderPowerRepository.deleteById(id);
    }

    @Override
    public BigInteger getOrderCount(String sql) {
        return null;
    }

    @Override
    public ArrayList<PowerModel> getOrdersBySql(String sql) {
        return null;
    }

    @Override
    public PowerModel findOrderByUid(long uid) {
        return orderPowerRepository.findByUidParamsNative(uid);
    }
}
