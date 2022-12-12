package com.epam.esm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomOrderDao {
    Page<Order> findUsersOrders(Long id, Pageable pageable);

    Optional<Order> findUserOrderById(Long userId, Long orderId);

}