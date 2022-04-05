package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.residenceModel.ResidenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResidenceTypeRepository extends JpaRepository<ResidenceType,Long> {

    @Override
    ResidenceType getOne(Long aLong);

    @Override
    Optional<ResidenceType> findById(Long aLong);

    @Override
    List<ResidenceType> findAllById(Iterable<Long> iterable);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_residence_type  where id= :id  and uid= :uid limit 1")
    ResidenceType findResidenceByIdAndUidParamsNative(@Param("id") long id, @Param("uid") long uid);

}
