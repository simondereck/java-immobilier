package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.residenceModel.ResidenceBookingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResidenceBookingRoomRepository extends JpaRepository<ResidenceBookingRoom,Long> {

    @Override
    ResidenceBookingRoom getOne(Long aLong);

    @Override
    Optional<ResidenceBookingRoom> findById(Long aLong);

    @Override
    List<ResidenceBookingRoom> findAllById(Iterable<Long> iterable);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_residence_booking_room  where id= :id  and uid= :uid limit 1")
    ResidenceBookingRoom findResidenceBookingRoomByIdAndUidParamsNative(@Param("id") long id, @Param("uid") long uid);


    @Query(nativeQuery = true, value =
            "select * from hw_residence_booking_room  where uid= :uid limit 1")
    ResidenceBookingRoom findResidenceBookingRoomByUidParamsNative(@Param("uid") long uid);

}
