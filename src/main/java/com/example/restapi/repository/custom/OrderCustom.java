package com.example.restapi.repository.custom;

import com.example.restapi.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//N+1 대비
public interface OrderCustom {

    @Query("select o from Order o join fetch o.member m where m.mid = :mid")
    List<Order> findOrderByMemberMid(@Param("mid") String mid);
}
