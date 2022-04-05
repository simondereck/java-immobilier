package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.residenceModel.ResidenceBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResidenceBookingRepository extends JpaRepository<ResidenceBooking,Long> {

    @Override
    ResidenceBooking getOne(Long aLong);

    @Override
    Optional<ResidenceBooking> findById(Long aLong);

    @Override
    List<ResidenceBooking> findAllById(Iterable<Long> iterable);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_residence_booking  where id= :id  and uid= :uid limit 1")
    ResidenceBooking findResidenceBookingByIdAndUidParamsNative(@Param("id") long id, @Param("uid") long uid);


}
