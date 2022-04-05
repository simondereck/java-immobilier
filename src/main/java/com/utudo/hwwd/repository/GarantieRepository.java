package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.Garantie;
import com.utudo.hwwd.models.Needs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GarantieRepository extends JpaRepository<Garantie,Long> {
    @Override
    Optional<Garantie> findById(Long aLong);

    @Override
    void delete(Garantie garantie);


    @Query(nativeQuery = true,value = "select * from hw_garantie where uid = :uid limit 1")
    Garantie findByUidParamsNative(@Param("uid") long uid);



    @Query(nativeQuery = true, value =
            "select * from hw_garantie  WHERE nid= :nid AND uid= :uid limit 1")
    Optional<Garantie> findGarantieByUidAndNidParamsNative(@Param("nid") long nid,@Param("uid") long uid);


    @Query(nativeQuery = true, value =
            "select * from hw_garantie  WHERE id= :id AND uid= :uid limit 1")
    Garantie findGarantieByIdAndUidParamsNative(@Param("id") long id,@Param("uid") long uid);
}
