package com.privat.payments.repository;

import com.privat.payments.model.Charge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChargeRepository extends JpaRepository<Charge, UUID> {

    @Query("SELECT ch " +
            "FROM Charge ch " +
            "JOIN ch.payment p " +
            "WHERE p.paymentId = :paymentId")
    List<Charge> findByRegularPaymentId(@Param("paymentId") UUID regularPaymentId);
}
