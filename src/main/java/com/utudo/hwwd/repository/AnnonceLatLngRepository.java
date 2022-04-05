package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.Annonce;
import com.utudo.hwwd.models.AnnonceLatLng;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnnonceLatLngRepository extends JpaRepository<AnnonceLatLng,Long> {
    @Override
    Optional<AnnonceLatLng> findById(Long id);


    @Override
    void delete(AnnonceLatLng latLng);


    @Query(nativeQuery = true,value = "select * from hw_annonce_lat_lng where pid = :pid limit 1")
    AnnonceLatLng findByPidParamsNative(@Param("pid") long pid);

    @Query(nativeQuery = true,value = "select * from hw_annonce_lat_lng where pid=:pid and aid=:aid limit 1")
    AnnonceLatLng findByPidAndAidParamsNative(@Param("pid") long pid,@Param("aid") long aid);


    @Query(nativeQuery = true,value = "select * from hw_annonce_lat_lng where pid=:pid and bid=:bid limit 1")
    AnnonceLatLng findByPidAndBidParamsNative(@Param("pid") long pid,@Param("bid") long bid);


}
