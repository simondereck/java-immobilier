package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.WebMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WebMessageRepository extends JpaRepository<WebMessage,Long> {

}
