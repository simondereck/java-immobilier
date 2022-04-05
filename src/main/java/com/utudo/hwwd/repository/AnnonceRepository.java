package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.Annonce;
import com.utudo.hwwd.models.Needs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AnnonceRepository extends JpaRepository<Annonce,Long> {
    @Override
    Optional<Annonce> findById(Long aLong);


    @Override
    void delete(Annonce annonce);


    @Query(nativeQuery = true,value = "select * from hw_annonce where pid = :pid limit 1")
    Annonce findByUidParamsNative(@Param("pid") long pid);

    @Query(nativeQuery = true,value = "select * from hw_annonce where pid = :pid and id=:id limit 1")
    Annonce findByPidAndIdParamsNative(@Param("pid") long pid,@Param("id") long id);

    @Query(nativeQuery = true,value = "select * from hw_annonce where pid = :pid")
    ArrayList<Annonce> findByPidParamsNative(@Param("pid") long pid);


    @Query(nativeQuery = true, value =
            "select * from hw_annonce  WHERE status= :status and pid = :pid")
    List<Annonce> findByStatusAndUidParamsNative(@Param("status") Integer status,@Param("pid") long pid);


    @Query(nativeQuery = true, value =
            "select * from hw_annonce  WHERE email= :email OR telephone= :email limit 1")
    Annonce findByEmailOrTelephoneParamsNative(@Param("email") String email);

    @Query(nativeQuery = true,value = "select * from hw_annonce where `status` > 0  order by `id` desc limit 4")
    ArrayList<Annonce> findLastedAnnonce();


    @Query(nativeQuery = true,value = "select * from hw_annonce where `pid`=:pid and `status` > 0 order  by `id` desc limit 1")
    Annonce findLastInsertParamsNative(@Param("pid") long pid);



    @Query(nativeQuery = true,value = "delete from hw_annonce where `pid`=:pid")
    void deleteByUidParamsNative(@Param("pid") long pid);
}
