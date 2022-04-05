package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.Admin;
import com.utudo.hwwd.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    Admin findById(long id);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_admin  where email= :email  or telephone= :email limit 1")
    Admin findByEmailOrTelephoneParamsNative( @Param("email") String email);


    @Query(nativeQuery = true,value = "select * from hw_admin where email=:email limit 1")
    User findAdminByEmailParamsNative(@Param("email") String email);


    @Query(nativeQuery = true,value = "select * from hw_admin where telephone=:telephone limit 1")
    User findAdminByTelephoneParamsNative(@Param("telephone") String telephone);

    @Query(nativeQuery = true,value = "select * from hw_user where ftoken = :ftoken limit 1")
    Admin findUserByTokenParamsNative(@Param("ftoken") String ftoken);

    @Query(nativeQuery = true,value = "select * from hw_admin where astatus=:status")
    ArrayList<Admin> findAdminByStatusParamsNative(@Param("status") int status);


    @Query(nativeQuery = true,value = "select * from hw_admin where token = :token limit 1")
    Admin findAdminByTokenParamsNative(@Param("token") String token);
}
