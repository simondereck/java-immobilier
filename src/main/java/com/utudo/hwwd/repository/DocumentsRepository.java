package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface DocumentsRepository extends JpaRepository<Documents,Long> {

    Documents findById(long id);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_documents  where uid = :uid limit 1")
    Documents findDocumentsByUidParamsNative(@Param("uid") long uid);


    @Query(nativeQuery = true,value = "select * from hw_documents order by id desc limit 1 ")
    Documents findLastedDocuments();

}
