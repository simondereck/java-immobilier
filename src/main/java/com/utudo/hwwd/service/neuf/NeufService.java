package com.utudo.hwwd.service.neuf;

import com.utudo.hwwd.models.neuf.NeufModel;
import java.math.BigInteger;
import java.util.ArrayList;

public interface NeufService {

    public void save(NeufModel residence);

    public void update(NeufModel residence);

    public BigInteger getCountBySql(String sql);

    public void delete(NeufModel residence);

    public ArrayList<NeufModel> getNeufBySql(String sql);

    public NeufModel findOneById(long id);

}
