package com.epam.esm;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Long> , CustomOrderDao {
}