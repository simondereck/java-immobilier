package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.WebMessageSqlRepository;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.WebMessage;
import com.utudo.hwwd.repository.WebMessageRepository;
import com.utudo.hwwd.service.WebMessageServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;

@Service
public class WebMessageServiceImpl implements WebMessageServer {

    @Autowired
    WebMessageRepository webMessageRepository;

    @Autowired
    WebMessageSqlRepository webMessageSqlRepository;

    @Override
    public void save(WebMessage webMessage) {
        String time = HwTools.getTime();
        webMessage.setCtime(time);
        webMessage.setUtime(time);
        webMessage.setStatus(HwDatas.WEB_MESSAGE_STATUS_UNREAD);
        webMessageRepository.save(webMessage);
    }

    @Override
    public void update(WebMessage webMessage) {
        webMessage.setStatus(HwDatas.WEB_MESSAGE_STATUS_READ);
        webMessage.setUtime(HwTools.getTime());
        webMessageRepository.save(webMessage);
    }

    @Override
    public void delete(long id) {
        webMessageRepository.deleteById(id);
    }

    @Override
    public ArrayList<WebMessage> getMessageByAidForm(long aid,long fromaid) {
        SqlHelper sqlHelper = new SqlHelper("hw_web_message");
        sqlHelper.addAndCondition("from_aid",fromaid);
        sqlHelper.addAndCondition("to_aid",aid);
        sqlHelper.addOrConditions("`from_aid` = " + aid + " and `to_aid` = " + fromaid);
        sqlHelper.setLimit("limit 15");
        sqlHelper.setOrder("id","desc");
        return webMessageSqlRepository.getWebMessageBySql(sqlHelper.toSql());


    }

    @Override
    public ArrayList<WebMessage> getMessageByAidTo(long aid,long toaid) {
        SqlHelper sqlHelper = new SqlHelper("hw_web_message");
        sqlHelper.addAndCondition("from_aid",aid);
        sqlHelper.addAndCondition("to_aid",toaid);
        sqlHelper.setLimit("limit 15");
        sqlHelper.setOrder("id","desc");
        return webMessageSqlRepository.getWebMessageBySql(sqlHelper.toSql());
    }

    @Override
    public BigInteger getMessageCount(long aid) {
        SqlHelper sqlHelper = new SqlHelper("hw_web_message");
        sqlHelper.setSelect("select count(*) ");
        sqlHelper.addAndCondition("to_aid",aid);
        sqlHelper.addAndCondition("status", HwDatas.WEB_MESSAGE_STATUS_UNREAD);
        return webMessageSqlRepository.getUnreadMessageBySql(sqlHelper.toSql());
    }


}
