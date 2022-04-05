package com.utudo.hwwd.service.impl.neuf;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.neuf.NeufModel;
import com.utudo.hwwd.repository.NeufRepository;
import com.utudo.hwwd.service.neuf.NeufService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;

@Service
public class NeufServiceImpl implements NeufService {

    @Autowired
    private NeufRepository neufRepository;

    @Autowired
    private SqlRepository sqlRepository;

    @Override
    public void delete(NeufModel neuf) {
        neufRepository.delete(neuf);
    }

    @Override
    public void save(NeufModel neuf) {
        String time = HwTools.getTime();
        neuf.setCtime(time);
        neuf.setUtime(time);
        neufRepository.save(neuf);
    }

    @Override
    public void update(NeufModel neuf) {
        String time = HwTools.getTime();
        neuf.setUtime(time);
        neufRepository.save(neuf);
    }

    @Override
    public ArrayList<NeufModel> getNeufBySql(String sql) {
        return sqlRepository.getNeufBySql(sql);
    }

    @Override
    public BigInteger getCountBySql(String sql) {
        return sqlRepository.getCountBySql(sql);
    }

    @Override
    public NeufModel findOneById(long id) {
        return neufRepository.getOne(id);
    }

}
