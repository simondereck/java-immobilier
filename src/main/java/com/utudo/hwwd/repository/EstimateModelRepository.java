package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.models.EstimateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstimateModelRepository extends JpaRepository<EstimateModel,Long> {


    EstimateModel findById(long id);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_estimate  where id_mutation = :estId limit 1")
    EstimateModel findEstimateByIdMutationParamsNative(@Param("estId") String estId);

}
