package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;


public interface UserRepository extends JpaRepository<User,Long> {


    User findById(long id);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_user  where email= :email  or telephone= :email limit 1")
    User findByEmailOrTelephoneParamsNative( @Param("email") String email);


    @Query(nativeQuery = true,value = "select * from hw_user where email=:email limit 1")
    User findUserByEmailParamsNative(@Param("email") String email);


    @Query(nativeQuery = true,value = "select * from hw_user where telephone=:telephone limit 1")
    User findUserByTelephoneParamsNative(@Param("telephone") String telephone);

    @Query(nativeQuery = true,value = "select * from hw_user where ftoken = :ftoken limit 1")
    User findUserByTokenParamsNative(@Param("ftoken") String ftoken);

    @Query(nativeQuery = true,value = "select * from hw_user  order by `utime` desc limit 4")
    ArrayList<User> findLastedUser();

    @Query(nativeQuery = true, value =
            "select * from hw_user  where email= :email  and telephone= :telephone limit 1")
    User findByEmailAndTelephoneParamsNative( @Param("email") String email,@Param("telephone") String telephone);
}
