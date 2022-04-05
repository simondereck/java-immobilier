package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.AnnonceBussiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AnnonceBussinessRepository extends JpaRepository<AnnonceBussiness,Long> {
    @Override
    Optional<AnnonceBussiness> findById(Long aLong);


    @Override
    void delete(AnnonceBussiness annonce);


    @Query(nativeQuery = true,value = "select * from hw_annonce_bussiness where pid = :pid limit 1")
    AnnonceBussiness findByUidParamsNative(@Param("pid") long pid);

    @Query(nativeQuery = true,value = "select * from hw_annonce_bussiness where pid = :pid and id=:id limit 1")
    AnnonceBussiness findByPidAndIdParamsNative(@Param("pid") long pid,@Param("id") long id);

    @Query(nativeQuery = true,value = "select * from hw_annonce_bussiness where pid = :pid")
    ArrayList<AnnonceBussiness> findByPidParamsNative(@Param("pid") long pid);


    @Query(nativeQuery = true, value =
            "select * from hw_annonce_bussiness  WHERE status= :status and pid = :pid")
    List<AnnonceBussiness> findByStatusAndUidParamsNative(@Param("status") Integer status,@Param("pid") long pid);


    @Query(nativeQuery = true, value =
            "select * from hw_annonce_bussiness  WHERE email= :email OR telephone= :email limit 1")
    AnnonceBussiness findByEmailOrTelephoneParamsNative(@Param("email") String email);

    @Query(nativeQuery = true,value = "select * from hw_annonce_bussiness order by `id` desc limit 8")
    ArrayList<AnnonceBussiness> findLastedAnnonce();


    @Query(nativeQuery = true,value = "select * from hw_annonce_bussiness where `house_label` = 3 or `house_label`= 4 order  by `id` desc limit 8")
    ArrayList<AnnonceBussiness> findLastedPlatform();

    @Query(nativeQuery = true,value = "select * from hw_annonce_bussiness where `pid` = :pid order  by `id` desc limit 1")
    AnnonceBussiness findLastInsertParamsNative(@Param("pid") long pid);


    @Query(nativeQuery = true,value = "delete from hw_annonce_bussiness where `pid` = :pid")
    void deleteByUidParamsNative(@Param("pid") long pid);
}
