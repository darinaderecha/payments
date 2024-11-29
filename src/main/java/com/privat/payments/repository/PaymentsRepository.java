package com.privat.payments.repository;

import com.privat.payments.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentsRepository extends JpaRepository<Payment, UUID> {

    @Query("SELECT p " +
            "FROM Payment p " +
            "JOIN p.card c " +
            "JOIN c.client cl " +
            "WHERE cl.itn = :itn")
    Page<Payment> findByClientITN(@Param("itn") String itn, Pageable page);

    @Query("SELECT p " +
            "FROM Payment p " +
            "WHERE p.zkpo = :zkpo")
    Page<Payment> findByReceiverZKPO(@Param("zkpo") String zkpo, Pageable page);
}
