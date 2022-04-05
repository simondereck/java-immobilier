package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AlarmRepository extends JpaRepository<Alarm,Long> {

    Alarm findById(long id);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_alarm  where date= :date limit 1")
    Alarm findAlarmByDateParamsNative(@Param("date") String date);


    @Query(nativeQuery = true,value = "select * from hw_alarm order by id desc limit 1 ")
    Alarm findLastedAlarm();

}
