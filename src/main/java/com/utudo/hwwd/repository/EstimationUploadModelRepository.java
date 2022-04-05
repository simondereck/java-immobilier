package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.EstimationUploadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstimationUploadModelRepository extends JpaRepository<EstimationUploadModel,Long> {

    EstimationUploadModel findById(long id);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_estimate_upload  where uid = :uid limit 1")
    EstimationUploadModel findEstimateByUidParamsNative(@Param("uid") long uid);

    @Query(nativeQuery = true,value = "select * from hw_estimation_upload where uid=:uid and id=:id limit 1")
    EstimationUploadModel findModelByUidAndIdParamsNative(@Param("uid") long uid,@Param("id") long id);
}
