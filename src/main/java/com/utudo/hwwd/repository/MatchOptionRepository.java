package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.MatchOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface MatchOptionRepository extends JpaRepository<MatchOption,Long> {

    @Override
    Optional<MatchOption> findById(Long aLong);



    @Query(nativeQuery = true, value =
            "select * from hw_match_option  where pid= :pid and  nid= :nid and aid=:aid  limit 1")
    Optional<MatchOption> findIfExistParamsNative(@Param("aid") long aid,@Param("pid") long pid,@Param("nid") long nid);

    @Query(nativeQuery = true,value = "select * from hw_match_option where pid = :pid and aid = :aid")
    ArrayList<MatchOption> getMatchOptionsByPidAndAidParamsNative(@Param("aid") long aid,@Param("pid") long pid);
}
