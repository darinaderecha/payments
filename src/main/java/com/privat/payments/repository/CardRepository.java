package com.privat.payments.repository;

import com.privat.payments.model.Card;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    @Query("SELECT c " +
            "FROM Card c " +
            "WHERE c.iban = :iban")
    Optional<Card> findByIBAN(@Param("iban") String IBAN);
}
