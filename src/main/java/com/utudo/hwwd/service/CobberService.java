package com.utudo.hwwd.service;
import com.utudo.hwwd.models.Cobber;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface CobberService {

    public List<Cobber> getCobberList();

    public Cobber findCobberById(long id);

    public void save(Cobber cobber);

    public void emailSave(Cobber cobber);

    public void update(Cobber cobber);

    public void delete(long id);

    public Cobber checkCobber(String email, String telephone);


    public Cobber findCobberByEmailOrTelephone(String email);

    public Cobber findCobberByToken(String ftoken);

    public Cobber findCobberByEmail(String email);

    public Cobber findCobberByTelephone(String telephone);


    public BigInteger getCobberCount(String sql);

    public ArrayList<Cobber> getCobbersBySql(String sql);

    public ArrayList<Cobber> getCobbersByIds(ArrayList<Object> ids);

    public ArrayList<Cobber> getLastedCobbers();

    public ArrayList<Cobber> getCobberEmail();

}
