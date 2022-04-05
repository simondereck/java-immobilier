package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.DemandeBussiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;


public interface DemandeBussinessRepository extends JpaRepository<DemandeBussiness,Long> {

    DemandeBussiness findById(long id);

    @Override
    void deleteById(Long aLong);


    @Query(nativeQuery = true, value =
            "select * from hw_bussiness_demander  WHERE uid= :uid  limit 1")
    DemandeBussiness findByUidParamsNative(@Param("uid") long uid);

    @Query(nativeQuery = true,value = "select * from hw_bussiness_demander where pid = :pid and id=:id limit 1")
    DemandeBussiness findByPidAndIdParamsNative(@Param("pid") long pid, @Param("id") long id);

    @Query(nativeQuery = true,value = "select * from hw_bussiness_demander order by `id` desc limit 8")
    ArrayList<DemandeBussiness> findLastedDemande();

}
