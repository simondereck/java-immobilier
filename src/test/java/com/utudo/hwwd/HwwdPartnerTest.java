package com.utudo.hwwd;

import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.Partner;
import com.utudo.hwwd.service.impl.PartnerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class HwwdPartnerTest extends HwwdMainTest{

    @Resource
    PartnerServiceImpl partnerService;
    /**
     * Todo generate partner user by this method --- test this
     */

    List<String> prix = new ArrayList<String>(){{
        add("A");
        add("B");
        add("C");
        add("E");
        add("F");
        add("G");
        add("H");
        add("I");
        add("J");
        add("K");
        add("L");
        add("M");
        add("N");
        add("O");
        add("P");
        add("Q");
        add("R");
        add("S");
        add("T");
        add("U");
        add("V");
        add("W");
        add("X");
        add("Y");
        add("Z");
    }};

    ArrayList<String> suffix = new ArrayList<>(){{
        add("@qq.com");
        add("@163.com");
        add("@gmail.com");
        add("@126.com");
        add("@icloud.com");
        add("@souhu.com");
        add("@yahoo.com");
    }};




//    @Test
//    @Rollback(value = false)
    void createPartner(){
        Random rand = new Random();
        String nom = "Ye";
        String prenom = "Liang";
        String password = "123456";
        for (int i = 0; i < 100; i++) {
            Partner partner = new Partner();
            int sex = rand.nextInt(2);
            partner.setSex(sex);
            partner.setNom(nom+i);
            partner.setPrenom(prenom+i);
            partner.setPassword(password);
            partner.setTelephone("0656759257");
            int sizeLength  = rand.nextInt(9);
            StringBuilder builderEmail = new StringBuilder();
            for (int j = 0; j < sizeLength; j++) {
                String item = "";
                int skip = rand.nextInt(2);
                if (skip==0){
                    if (j!=0){
                        item = rand.nextInt(10) + "";
                    }
                }else{
                    item = prix.get(rand.nextInt(prix.size()));
                }

                builderEmail.append(item);
            }
            builderEmail.append(suffix.get(rand.nextInt(suffix.size())));
            partner.setEmail(builderEmail.toString());
            partnerService.save(partner);
        }


    }


}
