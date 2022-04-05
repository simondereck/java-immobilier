package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.Needs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface NeedsRepository extends JpaRepository<Needs,Long>{


    @Override
    Optional<Needs> findById(Long aLong);

    @Override
    void delete(Needs needs);


    @Query(nativeQuery = true,value = "select * from hw_needs where uid = :uid limit 1")
    Needs findByUidParamsNative(@Param("uid") long uid);


    @Query(nativeQuery = true, value =
            "select * from hw_needs  WHERE uid= :uid AND id= :id limit 1")
    Needs findByUidAndIdParamsNative(@Param("uid") long uid,@Param("id") long id);


    @Query(nativeQuery = true, value =
            "select * from hw_needs  WHERE budget>= :budget AND location= :location And rtype= :rtype and sdate>=:sdate and imms_json = :immsJson or envir_json= :envirJson limit 20")
    ArrayList<Needs> matchNeedsListsParamsNative(@Param("budget") Float budget, @Param("location") Integer location, @Param("rtype") Integer rtype, @Param("sdate") String sdate, @Param("immsJson") String immsJson, @Param("envirJson") String envirJson);


    @Query(nativeQuery = true, value =
            "select * from hw_needs  WHERE budget>= :budget AND location= :location And rtype= :rtype and sdate>=:sdate limit 20")
    ArrayList<Needs> matchNeedsListsSecondaryParamsNative(@Param("budget") Float budget, @Param("location") Integer location, @Param("rtype") Integer rtype, @Param("sdate") String sdate);




    @Query(nativeQuery = true, value =
            "select * from hw_needs  WHERE budget>= :budget AND location= :location And rtype= :rtype limit 20")
    ArrayList<Needs> matchNeedsListsWeekParamsNative(@Param("budget") Float budget, @Param("location") Integer location, @Param("rtype") Integer rtype);


    @Query(nativeQuery = true,value = "select * from hw_needs where uid=:uid")
    ArrayList<Needs> getNeedsByUidParamsNative(@Param("uid") long uid);


    @Query(nativeQuery = true,value = "select * from hw_needs order by `utime` desc limit 4")
    ArrayList<Needs> findLastedNeeds();
}
