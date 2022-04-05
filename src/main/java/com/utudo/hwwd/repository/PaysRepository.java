package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.Pays;
import com.utudo.hwwd.models.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PaysRepository extends JpaRepository<Pays,Long> {


    @Override
    Pays getOne(Long aLong);

    @Override
    Optional<Pays> findById(Long aLong);

    @Override
    List<Pays> findAllById(Iterable<Long> iterable);

    @Override
    void deleteById(Long aLong);



    @Query(nativeQuery = true,value = "select id from hw_ville where code=:code")
    ArrayList<Object> findIdsByCodeParamsNative(@Param("code") Integer code);

//
//    @Query(nativeQuery = true,value = "select * from hw_ville where code=:code limit 1")
//    Ville findOneByCodeParamsNative(@Param("code") Integer code);
//
//    @Query(nativeQuery = true,value = "select * from hw_ville where postcode=:postcode limit 1")
//    Ville findOneByPostcodeParamsNative(@Param("postcode") String postcode);


}
