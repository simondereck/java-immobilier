package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.BussinessModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BussinessModelRepository extends JpaRepository<BussinessModel,Long> {

    BussinessModel findById(long id);

    @Override
    void deleteById(Long aLong);


    @Query(nativeQuery = true, value =
            "select * from hw_bussiness_model  WHERE uid = :uid  limit 1")
    BussinessModel findByUidParamsNative(@Param("uid") long uid);

}
