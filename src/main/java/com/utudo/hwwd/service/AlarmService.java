package com.utudo.hwwd.service;

import com.utudo.hwwd.models.AdminLocation;
import com.utudo.hwwd.models.Alarm;

public interface AlarmService {

    public Alarm findAlarmDate(String date);


    public Alarm findLastedAlarm();

    public void save(Alarm alarm);


    public void update(Alarm alarm);

}
