package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface PartnerRepository extends JpaRepository<Partner,Long> {

    Partner findById(long id);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_partner  WHERE email= :email OR telephone= :email limit 1")
    Partner findByEmailOrTelephoneParamsNative( @Param("email") String email);


    @Query(nativeQuery = true,value = "select * from hw_partner where ftoken = :ftoken limit 1")
    Partner findByFtokenParamsNative(@Param("ftoken") String ftoken);

    @Query(nativeQuery = true,value = "select * from hw_partner where email = :email limit 1")
    Partner findUserByEmailParamsNative(@Param("email") String email);

    @Query(nativeQuery = true,value = "select * from hw_partner where telephone = :telephone limit 1")
    Partner findUserByTelephoneParamsNative(@Param("telephone") String telephone);

    @Query(nativeQuery = true,value = "select * from hw_partner order by `id` desc limit 4")
    ArrayList<Partner> findLastedUsersParamsNative();

    @Query(nativeQuery = true,value = "select * from hw_partner order by `id` desc limit 1")
    Partner getLastOne();
}
