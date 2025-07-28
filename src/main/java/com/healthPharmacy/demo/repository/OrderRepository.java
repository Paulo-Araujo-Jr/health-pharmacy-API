package com.healthPharmacy.demo.repository;

import com.healthPharmacy.demo.enums.OrderStatus;
import com.healthPharmacy.demo.models.CustomerModel;
import com.healthPharmacy.demo.models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {

    Optional<OrderModel> findByCustomerAndStatus(CustomerModel customer, OrderStatus status);

    List<OrderModel> findAllByCustomerAndStatus(CustomerModel customerModel, OrderStatus status);

    Page<OrderModel> findAllByStatus(OrderStatus status, Pageable pageable);
}
