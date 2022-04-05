package com.utudo.hwwd.component;

import com.utudo.hwwd.controllers.MainController;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwEmail;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.Admin;
import com.utudo.hwwd.models.Alarm;
import com.utudo.hwwd.models.User;
import com.utudo.hwwd.service.AdminService;
import com.utudo.hwwd.service.impl.AdminServiceImpl;
import com.utudo.hwwd.service.impl.AlarmServiceImpl;
import com.utudo.hwwd.service.impl.AnnonceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class AlarmComponent extends MainController {

    @Resource
    AnnonceServiceImpl annonceService;

    @Resource
    AlarmServiceImpl alarmService;

    @Resource
    AdminServiceImpl adminService;


    @Autowired
    private HwEmail  mailUtils;

    @Scheduled(fixedRate = 4*3600*1000)
    public void scheduledTask() throws UnsupportedEncodingException {
        System.out.println("任务执行时间：" + LocalDateTime.now());
        HashMap<Integer, BigInteger> counts = annonceService.getAnnonceCounts();
        Alarm alarm = alarmService.findAlarmDate(HwTools.getDay());
        if (alarm!=null){
            alarm.setTotalAnnonces(Long.parseLong(counts.get(0)+""));
            alarm.setSuccessAnnonces(Long.parseLong(counts.get(1)+""));
            alarm.setContractAnnonces(Long.parseLong(counts.get(2)+""));
            alarm.setEmptyAnnonces(Long.parseLong(counts.get(3)+""));
            alarmService.update(alarm);
        }else{
            alarm = new Alarm();
            alarm.setTotalAnnonces(Long.parseLong(counts.get(0)+""));
            alarm.setSuccessAnnonces(Long.parseLong(counts.get(1)+""));
            alarm.setContractAnnonces(Long.parseLong(counts.get(2)+""));
            alarm.setEmptyAnnonces(Long.parseLong(counts.get(3)+""));
            alarmService.save(alarm);
        }

        float percent = ((float) alarm.getEmptyAnnonces()/(float) alarm.getTotalAnnonces())*100;
        if (percent>=15){
            ArrayList<Admin> admins =adminService.findAdminByStatus(HwDatas.PERSON_TYPE_ADMIN_STATUS_ACTIVE);
            HashMap<String,Object> data = new HashMap<String,Object>();

            if (percent<20){
                data.put("message","空房源占比超过15%，周六需要全体员工加班");

            }else{
                data.put("message","空房源占比超过20%，周六、周日需要全体员工加班");
            }

            String templatePath =  "mail/alarm.html";

            if (admins!=null&&admins.size()>0){
                for (Admin admin : admins) {
                    logger.error("emails:++++++"+admin.getEmail());
                    try{
                        mailUtils.sendThymeleafMail("【Hwwd加班通知】", admin.getEmail(),data,templatePath) ;
                    }catch (Exception exception){
                        exception.printStackTrace();
                        logger.error("到底哪里出错了:"+exception.toString());
                    }
                }
            }
        }

    }




}
