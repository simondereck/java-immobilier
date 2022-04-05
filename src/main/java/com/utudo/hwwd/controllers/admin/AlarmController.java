package com.utudo.hwwd.controllers.admin;

import com.utudo.hwwd.controllers.MainController;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.Alarm;
import com.utudo.hwwd.service.impl.AlarmServiceImpl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;


@RestController
public class AlarmController extends MainController {

    @Resource
    AlarmServiceImpl alarmService;

    @RequestMapping(value = "/admin/alarm")
    public ModelAndView alarmDetail(ModelAndView mv){
        mv.setViewName("alarm/index");
        Alarm alarm;

        alarm = alarmService.findAlarmDate(HwTools.getDay());
        if (alarm==null){
            alarm = alarmService.findLastedAlarm();
        }

        mv.addObject("alarm",alarm);
        return mv;
    }

    @RequestMapping(value = "/admin/alarm/detail")
    public Object alarmMap(){
        HashMap<String,Object> data = new HashMap<>();
        Alarm alarm;
        alarm = alarmService.findAlarmDate(HwTools.getDay());
        if (alarm==null){
            alarm = alarmService.findLastedAlarm();
        }
        data.put("status",1);
        data.put("alarm",alarm);
        data.put("user",HwTools.getCurrentUser());
        return data;
    }



}
