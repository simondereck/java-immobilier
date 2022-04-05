package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.Alarm;
import com.utudo.hwwd.repository.AlarmRepository;
import com.utudo.hwwd.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    AlarmRepository alarmRepository;


    @Override
    public Alarm findAlarmDate(String date) {
        return alarmRepository.findAlarmByDateParamsNative(date);
    }

    @Override
    public Alarm findLastedAlarm() {
        return alarmRepository.findLastedAlarm();
    }


    @Override
    public void save(Alarm alarm) {
        String time = HwTools.getTime();
        alarm.setDate(HwTools.getDay());
        alarm.setUtime(time);
        alarm.setCtime(time);
        alarmRepository.save(alarm);
    }

    @Override
    public void update(Alarm alarm) {
        alarm.setUtime(HwTools.getTime());
        alarmRepository.save(alarm);
    }
}
