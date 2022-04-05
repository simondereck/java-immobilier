package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.AdminLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface AdminLoginRepository extends JpaRepository<AdminLogin,Long> {

    AdminLogin findById(long id);

    @Override
    void deleteById(Long aLong);


    @Query(nativeQuery = true,value = "select * from hw_admin_login where uid=:uid and date=:date")
    ArrayList<AdminLogin> findLoginsByUidAndDateParamsNative(@Param("uid") long uid,@Param("date") String date);



}
