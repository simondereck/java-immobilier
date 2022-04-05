package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.Cobber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface CobberRepository extends JpaRepository<Cobber,Long> {

    Cobber findById(long id);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_cobber  WHERE email= :email OR telephone= :email limit 1")
    Cobber findByEmailOrTelephoneParamsNative( @Param("email") String email);


    @Query(nativeQuery = true,value = "select * from hw_cobber where ftoken = :ftoken limit 1")
    Cobber findByFtokenParamsNative(@Param("ftoken") String ftoken);

    @Query(nativeQuery = true,value = "select * from hw_cobber where email = :email limit 1")
    Cobber findCobberByEmailParamsNative(@Param("email") String email);

    @Query(nativeQuery = true,value = "select * from hw_cobber where telephone = :telephone limit 1")
    Cobber findCobberByTelephoneParamsNative(@Param("telephone") String telephone);

    @Query(nativeQuery = true,value = "select * from hw_cobber order by `id` limit 4")
    ArrayList<Cobber> findLastedUsersParamsNative();
}
