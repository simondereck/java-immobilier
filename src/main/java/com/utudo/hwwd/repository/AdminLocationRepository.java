package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface AdminLocationRepository extends JpaRepository<AdminLocation,Long> {

    AdminLocation findById(long id);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_admin_location  where aid= :aid  and date= :date limit 1")
    AdminLocation findAdminLocationByAidAndDateParamsNative( @Param("aid") long aid,@Param("date") String date);


    @Query(nativeQuery = true,value = "select * from hw_admin_location where aid=:aid")
    ArrayList<AdminLocation> findAdminLocationByAidParamsNative(@Param("aid") long aid);

    @Query(nativeQuery = true,value = "select * from hw_admin_location where aid=:aid order by id desc limit 1")
    AdminLocation findLastedAdminLocationByAidParamsNative(@Param("aid") long aid);

}
