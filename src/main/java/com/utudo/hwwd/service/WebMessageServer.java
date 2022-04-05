package com.utudo.hwwd.service;

import com.utudo.hwwd.models.WebMessage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface WebMessageServer {



    public void save(WebMessage webMessage);

    public void update(WebMessage webMessage);

    public void delete(long id);



    public ArrayList<WebMessage> getMessageByAidForm(long aid,long fromaid);

    public ArrayList<WebMessage> getMessageByAidTo(long aid,long toaid);


    public BigInteger getMessageCount(long aid);

}
