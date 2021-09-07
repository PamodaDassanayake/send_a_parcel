package com.api.sendaparcel.api.repository;

import com.api.sendaparcel.api.entity.Parcel;
import com.api.sendaparcel.api.entity.User;
import com.api.sendaparcel.api.util.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Parcel,Long> {
    Page<Parcel> findParcelsByOrderStatus(OrderStatus orderStatus, Pageable pageable);
    Page<Parcel> findParcelsByCustomer(User customer, Pageable pageable);
    Page<Parcel> findParcelsByAssignedDriver(User driver, Pageable pageable);
    Page<Parcel> findParcelsByCustomerAndAndOrderStatus(User customer, OrderStatus orderStatus, Pageable pageable);
    Page<Parcel> findParcelsByAssignedDriverAndOrderStatus(User driver, OrderStatus orderStatus, Pageable pageable);
}
