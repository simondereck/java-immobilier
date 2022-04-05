package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.CobberShop;
import com.utudo.hwwd.models.cobber.PowerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface OrderPowerRepository extends JpaRepository<PowerModel,Long> {

    PowerModel findById(long id);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_cobber_power  WHERE cid= :uid  limit 1")
    PowerModel findByUidParamsNative(@Param("uid") long uid);


}
