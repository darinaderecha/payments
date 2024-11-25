package com.privat.payments.repository;

import com.privat.payments.model.Payment;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegularPaymentsRepository extends JpaRepository<Payment, UUID> {

    @Query("SELECT p " +
            "FROM Payment p " +
            "JOIN p.card c " +
            "JOIN c.client cl " +
            "WHERE cl.tin = :tin")
    List<Payment> findByClientTIN(@Param("tin") String tin);

    @Query("SELECT p " +
            "FROM Payment p " +
            "WHERE p.zkpo = :zkpo")
    List<Payment> findReceiverZKPO(@Param("zkpo") String zkpo);

}
