package com.privat.payments;

import com.privat.payments.controller.ChargeController;
import com.privat.payments.dto.ChargeCreateDto;
import com.privat.payments.dto.ChargeDto;
import com.privat.payments.model.*;
import com.privat.payments.repository.ChargeRepository;
import com.privat.payments.repository.PaymentsRepository;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChargeControllerTest {

    private ChargeRepository chargeRepository;
    private PaymentsRepository paymentsRepository;
    private ChargeController chargeController;

    @BeforeEach
    public void setUp() {
        chargeRepository = Mockito.mock(ChargeRepository.class);
        paymentsRepository = Mockito.mock(PaymentsRepository.class);
        chargeController = new ChargeController(chargeRepository, paymentsRepository);
    }

    //createCharge

    @Test
     void givenMockedDependencies_WhenCreateChargeInvoked_ThenReturnsExpectedChargeDto() {
        UUID paymentId = UUID.randomUUID();
        UUID chargeId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = BigDecimal.valueOf(100.00);
        Status status =Status.ACTIVE;

        ChargeCreateDto chargeCreateDto = new ChargeCreateDto(paymentId, now, amount, status);

        Payment mockPayment = new Payment();
        mockPayment.setPaymentId(paymentId);

        Charge mockCharge = new Charge();
        mockCharge.setChargeId(chargeId);
        mockCharge.setPayment(mockPayment);
        mockCharge.setChargeTime(now);
        mockCharge.setAmount(amount);
        mockCharge.setStatus(status);

        Mockito.when(paymentsRepository.findById(paymentId)).thenReturn(Optional.of(mockPayment));
        Mockito.when(chargeRepository.save(Mockito.any(Charge.class))).thenReturn(mockCharge);

        ResponseEntity<ChargeDto> response = chargeController.createCharge(chargeCreateDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        ChargeDto chargeDto = response.getBody();
        assertNotNull(chargeDto);
        assertEquals(chargeId, chargeDto.chargeId());
        assertEquals(paymentId, chargeDto.payment());
        assertEquals(amount, chargeDto.amount());
        assertEquals(status, chargeDto.status());
        assertEquals(now, chargeDto.chargeTime());

        Mockito.verify(paymentsRepository).findById(paymentId);
        Mockito.verify(chargeRepository).save(Mockito.any(Charge.class));
    }

    @Test
    void testCreateChargeWithInvalidPaymentId() {
        UUID invalidPaymentId = UUID.randomUUID();
        ChargeCreateDto chargeCreateDto = new ChargeCreateDto(invalidPaymentId, LocalDateTime.now(), BigDecimal.valueOf(100.00), Status.ACTIVE);

        Mockito.when(paymentsRepository.findById(invalidPaymentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            chargeController.createCharge(chargeCreateDto);
        });

        assertEquals("Payment not found with ID: " + invalidPaymentId, exception.getMessage());
        Mockito.verify(paymentsRepository).findById(invalidPaymentId);
    }

    //updateCharge

    @Test
    void givenMockedDependencies_WhenUpdateChargeInvoked_ThenReturnsUpdatedChargeDto() {
        UUID chargeId = UUID.randomUUID();
        UUID existingPaymentId = UUID.randomUUID();
        UUID newPaymentId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = BigDecimal.valueOf(200.00);
        Status status = Status.ACTIVE;

        ChargeDto chargeDto = new ChargeDto(chargeId, newPaymentId, now, amount, status);

        Payment existingMockPayment = new Payment();
        existingMockPayment.setPaymentId(existingPaymentId);

        Payment newMockPayment = new Payment();
        newMockPayment.setPaymentId(newPaymentId);

        Charge mockCharge = new Charge();
        mockCharge.setChargeId(chargeId);
        mockCharge.setPayment(existingMockPayment);
        mockCharge.setChargeTime(now.minusDays(1));
        mockCharge.setAmount(BigDecimal.valueOf(100.00));
        mockCharge.setStatus(Status.SUSPENDED);

        Mockito.when(chargeRepository.findById(chargeId)).thenReturn(Optional.of(mockCharge));
        Mockito.when(paymentsRepository.findById(newPaymentId)).thenReturn(Optional.of(newMockPayment));
        Mockito.when(chargeRepository.save(Mockito.any(Charge.class))).thenReturn(mockCharge);

        ResponseEntity<ChargeDto> response = chargeController.updateCharge(chargeId, chargeDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        ChargeDto updatedChargeDto = response.getBody();
        assertNotNull(updatedChargeDto);
        assertEquals(chargeId, updatedChargeDto.chargeId());
        assertEquals(newPaymentId, updatedChargeDto.payment());
        assertEquals(amount, updatedChargeDto.amount());
        assertEquals(status, updatedChargeDto.status());

        Mockito.verify(chargeRepository).findById(chargeId);
        Mockito.verify(paymentsRepository).findById(newPaymentId);
        Mockito.verify(chargeRepository).save(Mockito.any(Charge.class));
    }

    @Test
     void testUpdateChargeWithInvalidChargeId() {
        UUID chargeId = UUID.randomUUID();
        UUID paymentId = UUID.randomUUID();
        ChargeDto chargeDto = new ChargeDto(chargeId, paymentId, LocalDateTime.now(), BigDecimal.valueOf(200.00), Status.ACTIVE);

        Mockito.when(chargeRepository.findById(chargeId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            chargeController.updateCharge(chargeId, chargeDto);
        });

        assertEquals("Charge not found with ID: " + chargeId, exception.getMessage());
        Mockito.verify(chargeRepository).findById(chargeId);
        Mockito.verifyNoInteractions(paymentsRepository);
        Mockito.verifyNoMoreInteractions(chargeRepository);
    }

    //deleteCharge

    @Test
    void givenMockedDependencies_WhenDeleteChargeInvoked_ThenChargeIsDeleted() {
        UUID chargeId = UUID.randomUUID();

        Mockito.when(chargeRepository.existsById(chargeId)).thenReturn(true);

        ResponseEntity<Void> response = chargeController.deleteCharge(chargeId);

        assertNotNull(response);
        assertEquals(204, response.getStatusCode().value());

        Mockito.verify(chargeRepository).existsById(chargeId);
        Mockito.verify(chargeRepository).deleteById(chargeId);
    }

    @Test
    void testDeleteChargeWithInvalidChargeId() {
        UUID chargeId = UUID.randomUUID();

        Mockito.when(chargeRepository.existsById(chargeId)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            chargeController.deleteCharge(chargeId);
        });

        assertEquals("Charge not found with ID: " + chargeId, exception.getMessage());
        Mockito.verify(chargeRepository).existsById(chargeId);
        Mockito.verifyNoMoreInteractions(chargeRepository);
    }

    //findChargeById

    @Test
    void givenMockedDependencies_WhenFindChargeByIdInvoked_ThenReturnsExpectedChargeDto() {
        UUID chargeId = UUID.randomUUID();
        UUID paymentId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = BigDecimal.valueOf(100.00);
        Status status = Status.ACTIVE;

        Payment payment = new Payment(paymentId,
                new Card(UUID.randomUUID(),
                        new Client(),
                        "1234567890123456"),
                "UA12345678901234567890123456",
                "123456789",
                "12345",
                "Petro Petrovych",
                BigDecimal.valueOf(1234L),
                100L);

        Charge mockCharge = new Charge();
        mockCharge.setChargeId(chargeId);
        mockCharge.setPayment(payment);
        mockCharge.setChargeTime(now);
        mockCharge.setAmount(amount);
        mockCharge.setStatus(status);

        Mockito.when(chargeRepository.findById(chargeId)).thenReturn(Optional.of(mockCharge));

        ResponseEntity<ChargeDto> response = chargeController.findChargeById(chargeId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        ChargeDto chargeDto = response.getBody();
        assertNotNull(chargeDto);
        assertEquals(chargeId, chargeDto.chargeId());
        assertEquals(paymentId, chargeDto.payment());
        assertEquals(amount, chargeDto.amount());
        assertEquals(status, chargeDto.status());
        assertEquals(now, chargeDto.chargeTime());

        Mockito.verify(chargeRepository).findById(chargeId);
    }

    @Test
    void testFindChargeByIdWithInvalidChargeId() {
        UUID chargeId = UUID.randomUUID();

        Mockito.when(chargeRepository.findById(chargeId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            chargeController.findChargeById(chargeId);
        });

        assertEquals("Charge not found with ID: " + chargeId, exception.getMessage());
        Mockito.verify(chargeRepository).findById(chargeId);
    }

    //findChargeByPaymentId

    @Test
    void givenMockedDependencies_WhenFindChargesByPaymentIdInvoked_ThenReturnsExpectedList() {
        UUID paymentId = UUID.randomUUID();
        UUID chargeId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = BigDecimal.valueOf(100.00);
        Status status = Status.ACTIVE;

        Payment payment = new Payment(paymentId,
                new Card(UUID.randomUUID(),
                        new Client(),
                        "1234567890123456"),
                "UA12345678901234567890123456",
                "123456789",
                "12345",
                "Petro Petrovych",
                BigDecimal.valueOf(1234L),
                100L);

        Charge mockCharge = new Charge();
        mockCharge.setChargeId(chargeId);
        mockCharge.setPayment(payment);
        mockCharge.setChargeTime(now);
        mockCharge.setAmount(amount);
        mockCharge.setStatus(status);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Charge> mockPage = new PageImpl<>(Collections.singletonList(mockCharge), pageable, 1);

        Mockito.when(chargeRepository.findByRegularPaymentId(paymentId, pageable)).thenReturn(mockPage);

        ResponseEntity<List<ChargeDto>> response = chargeController.findChargesByPaymentId(paymentId, pageable.getPageNumber(), pageable.getPageSize());

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        List<ChargeDto> chargeDtos = response.getBody();
        assertNotNull(chargeDtos);
        assertEquals(1, chargeDtos.size());
        ChargeDto chargeDto = chargeDtos.get(0);
        assertEquals(chargeId, chargeDto.chargeId());
        assertEquals(paymentId, chargeDto.payment());
        assertEquals(amount, chargeDto.amount());
        assertEquals(status, chargeDto.status());
        assertEquals(now, chargeDto.chargeTime());

        Mockito.verify(chargeRepository).findByRegularPaymentId(paymentId, pageable);
    }

    @Test
    void testFindChargesByPaymentIdWithNoCharges() {
        UUID paymentId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Charge> mockPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        Mockito.when(chargeRepository.findByRegularPaymentId(paymentId, pageable)).thenReturn(mockPage);

        ResponseEntity<List<ChargeDto>> response = chargeController.findChargesByPaymentId(paymentId, pageable.getPageNumber(), pageable.getPageSize());

        assertNotNull(response);
        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());

        Mockito.verify(chargeRepository).findByRegularPaymentId(paymentId, pageable);
    }


}