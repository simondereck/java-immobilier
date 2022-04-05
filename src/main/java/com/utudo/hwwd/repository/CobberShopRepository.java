package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.CobberShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CobberShopRepository extends JpaRepository<CobberShop,Long> {

    CobberShop findById(long id);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_cobber_shop  WHERE cid= :uid  limit 1")
    CobberShop findByUidParamsNative(@Param("uid") long uid);

}
