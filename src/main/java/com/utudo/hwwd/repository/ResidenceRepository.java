package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.residenceModel.Residence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResidenceRepository extends JpaRepository<Residence,Long> {

    @Override
    Residence getOne(Long aLong);

    @Override
    Optional<Residence> findById(Long aLong);

    @Override
    List<Residence> findAllById(Iterable<Long> iterable);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_residence  where id= :id  and uid= :uid limit 1")
    Residence findResidenceByIdAndUidParamsNative(@Param("id") long id, @Param("uid") long uid);

}
