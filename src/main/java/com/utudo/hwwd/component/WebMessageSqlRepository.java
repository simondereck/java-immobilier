package com.utudo.hwwd.component;


import com.utudo.hwwd.models.Needs;
import com.utudo.hwwd.models.WebMessage;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.ArrayList;

@Component
public class WebMessageSqlRepository {

    @PersistenceContext
    EntityManager entityManager;

    public ArrayList<WebMessage> getWebMessageBySql(String sql) {
         return (ArrayList<WebMessage>) entityManager.createNativeQuery(sql,WebMessage.class).getResultList();
    }

    public BigInteger getUnreadMessageBySql(String sql){
        return (BigInteger) entityManager.createNativeQuery(sql).getSingleResult();
    }

}
