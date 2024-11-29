package com.privat.payments;

import com.privat.payments.controller.PaymentController;
import com.privat.payments.dto.PaymentCreateDto;
import com.privat.payments.dto.PaymentDto;
import com.privat.payments.model.Card;
import com.privat.payments.model.Client;
import com.privat.payments.model.Payment;
import com.privat.payments.repository.CardRepository;
import com.privat.payments.repository.PaymentsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PaymentControllerTest {

    private PaymentsRepository paymentsRepository;
    private CardRepository cardRepository;
    private PaymentController paymentController;
    private Pageable pageable;

    @BeforeEach
    public void setUp() {
        paymentsRepository = mock(PaymentsRepository.class);
        cardRepository = mock(CardRepository.class);
        paymentController = new PaymentController(paymentsRepository, cardRepository);
        pageable = PageRequest.of(0, 8);
    }

    //savePayment

    @Test
    void testSavePayment() {
        UUID cardId = UUID.randomUUID();
        UUID paymentId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100.00);

        PaymentCreateDto paymentCreateDto = new PaymentCreateDto(cardId, "UA1234567890",
                "300335", "1234567890", "John Doe", amount, 1000L);

        Card mockCard = new Card();
        mockCard.setCardId(cardId);

        Payment mockPayment = new Payment();
        mockPayment.setPaymentId(paymentId);
        mockPayment.setCard(mockCard);
        mockPayment.setIban(paymentCreateDto.iban());
        mockPayment.setMfo(paymentCreateDto.mfo());
        mockPayment.setZkpo(paymentCreateDto.zkpo());
        mockPayment.setReceiverName(paymentCreateDto.receiverName());
        mockPayment.setAmount(paymentCreateDto.amount());
        mockPayment.setWithdrawalPeriod(paymentCreateDto.withdrawalPeriod());

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(mockCard));
        when(paymentsRepository.save(any(Payment.class))).thenReturn(mockPayment);

        ResponseEntity<PaymentDto> response = paymentController.savePayment(paymentCreateDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        PaymentDto dto = response.getBody();
        assertNotNull(dto);
        assertEquals(paymentId, dto.id());
        assertEquals(cardId, dto.card());
        assertEquals(amount, dto.amount());

        verify(cardRepository).findById(cardId);
        verify(paymentsRepository).save(any(Payment.class));
    }

    @Test
    void testSavePayment_CardNotFound() {
        UUID cardId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100.00);

        PaymentCreateDto paymentCreateDto = new PaymentCreateDto(cardId, "UA1234567890",
                "300335", "1234567890", "John Doe", amount, 1000L);

        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            paymentController.savePayment(paymentCreateDto);
        });

        assertEquals("Card not found with ID: " + cardId, exception.getMessage());
        verify(cardRepository).findById(cardId);
        verifyNoInteractions(paymentsRepository);
    }

    //update

    @Test
    void testUpdatePayment() {
        UUID paymentId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(200.00);

        PaymentCreateDto paymentCreateDto = new PaymentCreateDto(cardId, "UA9876543210",
                "123456", "9876543210", "Jane Doe", amount, 1000L);

        Card mockCard = new Card();
        mockCard.setCardId(cardId);

        Payment existingPayment = new Payment();
        existingPayment.setPaymentId(paymentId);
        existingPayment.setCard(mockCard);
        existingPayment.setIban("UA1234567890");
        existingPayment.setMfo("300335");
        existingPayment.setZkpo("1234567890");
        existingPayment.setReceiverName("John Doe");
        existingPayment.setAmount(BigDecimal.valueOf(100.00));
        existingPayment.setWithdrawalPeriod(1000l);

        when(paymentsRepository.findById(paymentId)).thenReturn(Optional.of(existingPayment));
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(mockCard));
        when(paymentsRepository.save(any(Payment.class))).thenReturn(existingPayment);

        ResponseEntity<PaymentDto> response = paymentController.updatePayment(paymentId, paymentCreateDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        PaymentDto dto = response.getBody();
        assertNotNull(dto);
        assertEquals(paymentId, dto.id());
        assertEquals(cardId, dto.card());
        assertEquals(amount, dto.amount());

        verify(paymentsRepository).findById(paymentId);
        verify(cardRepository).findById(cardId);
        verify(paymentsRepository).save(any(Payment.class));
    }

    @Test
    void testUpdatePayment_PaymentNotFound() {
        UUID paymentId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(200.00);

        PaymentCreateDto paymentCreateDto = new PaymentCreateDto(cardId, "UA9876543210",
                "123456", "9876543210", "Jane Doe", amount, 1000L);

        when(paymentsRepository.findById(paymentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            paymentController.updatePayment(paymentId, paymentCreateDto);
        });

        assertEquals("Payment not found with ID: " + paymentId, exception.getMessage());
        verify(paymentsRepository).findById(paymentId);
        verifyNoInteractions(cardRepository);
        verifyNoMoreInteractions(paymentsRepository);
    }

    @Test
    void testUpdatePayment_CardNotFound() {
        UUID paymentId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(200.00);

        PaymentCreateDto paymentCreateDto = new PaymentCreateDto(cardId, "UA9876543210",
                "123456", "9876543210", "Jane Doe", amount, 1000L);

        Payment existingPayment = new Payment();
        existingPayment.setPaymentId(paymentId);

        when(paymentsRepository.findById(paymentId)).thenReturn(Optional.of(existingPayment));
        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            paymentController.updatePayment(paymentId, paymentCreateDto);
        });

        assertEquals("Card not found with ID: " + cardId, exception.getMessage());
        verify(paymentsRepository).findById(paymentId);
        verify(cardRepository).findById(cardId);
        verifyNoMoreInteractions(paymentsRepository);
    }


    //delete

    @Test
    void testDeletePayment() {
        UUID paymentId = UUID.randomUUID();
        Payment mockPayment = new Payment();
        mockPayment.setPaymentId(paymentId);

        when(paymentsRepository.findById(paymentId)).thenReturn(Optional.of(mockPayment));

        ResponseEntity<Void> response = paymentController.deletePayment(paymentId);

        assertNotNull(response);
        assertEquals(204, response.getStatusCode().value());

        verify(paymentsRepository).findById(paymentId);
        verify(paymentsRepository).delete(mockPayment);
    }

    @Test
    void testDeletePayment_PaymentNotFound() {
        UUID paymentId = UUID.randomUUID();

        when(paymentsRepository.findById(paymentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            paymentController.deletePayment(paymentId);
        });

        assertEquals("Payment not found with ID: " + paymentId, exception.getMessage());
        verify(paymentsRepository).findById(paymentId);
        verifyNoMoreInteractions(paymentsRepository);
    }


    //findById

    @Test
    void testFindPaymentById() {
        UUID paymentId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100.00);

        Card mockCard = new Card();
        mockCard.setCardId(cardId);

        Payment mockPayment = new Payment();
        mockPayment.setPaymentId(paymentId);
        mockPayment.setCard(mockCard);
        mockPayment.setIban("UA1234567890");
        mockPayment.setMfo("300335");
        mockPayment.setZkpo("1234567890");
        mockPayment.setReceiverName("John Doe");
        mockPayment.setAmount(amount);
        mockPayment.setWithdrawalPeriod(1234l);

        when(paymentsRepository.findById(paymentId)).thenReturn(Optional.of(mockPayment));

        ResponseEntity<PaymentDto> response = paymentController.findPaymentById(paymentId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        PaymentDto dto = response.getBody();
        assertNotNull(dto);
        assertEquals(paymentId, dto.id());
        assertEquals(cardId, dto.card());
        assertEquals(amount, dto.amount());

        verify(paymentsRepository).findById(paymentId);
    }

    @Test
    void testFindPaymentById_PaymentNotFound() {
        UUID paymentId = UUID.randomUUID();

        when(paymentsRepository.findById(paymentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            paymentController.findPaymentById(paymentId);
        });

        assertEquals("Payment not found with ID: " + paymentId, exception.getMessage());
        verify(paymentsRepository).findById(paymentId);
    }

    //findByITN

    @Test
    void testFindPaymentByITN() {
        String itn = "1234567890";
        UUID cardId = UUID.randomUUID();
        UUID paymentId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100.00);

        Card mockCard = new Card();
        mockCard.setCardId(cardId);

        Payment mockPayment = new Payment();
        mockPayment.setPaymentId(paymentId);
        mockPayment.setCard(mockCard);
        mockPayment.setIban("UA1234567890");
        mockPayment.setMfo("300335");
        mockPayment.setZkpo("1234567890");
        mockPayment.setReceiverName("Kolya Kol");
        mockPayment.setAmount(amount);
        mockPayment.setWithdrawalPeriod(1000L);

        Page<Payment> mockPage = new PageImpl<>(Collections.singletonList(mockPayment), pageable, 1);

        when(paymentsRepository.findByClientITN(itn, pageable)).thenReturn(mockPage);

        ResponseEntity<List<PaymentDto>> response = paymentController.findPaymentByITN(itn, pageable.getPageNumber(), pageable.getPageSize());

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(mockPayment.getPaymentId(), response.getBody().getFirst().id());

        verify(paymentsRepository).findByClientITN(itn, pageable);
    }

    @Test
    void testFindPaymentByITN_NoPaymentsFound() {
        String itn = "1234567890";
        Page<Payment> mockPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(paymentsRepository.findByClientITN(itn, pageable)).thenReturn(mockPage);

        ResponseEntity<List<PaymentDto>> response = paymentController.findPaymentByITN(itn, pageable.getPageNumber(), pageable.getPageSize());

        assertNotNull(response);
        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());

        verify(paymentsRepository).findByClientITN(itn, pageable);
    }


    //findbyZKPO

    @Test
    void testFindPaymentByZKPO_WithResults() {
        String zkpo = "1234567890";
        UUID cardId = UUID.randomUUID();
        UUID paymentId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100.00);

        Card mockCard = new Card();
        mockCard.setCardId(cardId);

        Payment mockPayment = new Payment();
        mockPayment.setPaymentId(paymentId);
        mockPayment.setCard(mockCard);
        mockPayment.setIban("UA1234567890");
        mockPayment.setMfo("300335");
        mockPayment.setZkpo(zkpo);
        mockPayment.setReceiverName("Kolya Kol");
        mockPayment.setAmount(amount);
        mockPayment.setWithdrawalPeriod(1000L);

        Page<Payment> mockPage = new PageImpl<>(Collections.singletonList(mockPayment), pageable, 1);

        when(paymentsRepository.findByReceiverZKPO(zkpo, pageable)).thenReturn(mockPage);

        ResponseEntity<List<PaymentDto>> response = paymentController.findPaymentByZKPO(zkpo, pageable.getPageNumber(), pageable.getPageSize());

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        List<PaymentDto> paymentDtos = response.getBody();
        assertNotNull(paymentDtos);
        assertEquals(1, paymentDtos.size());
        assertEquals(zkpo, paymentDtos.getFirst().zkpo());

        verify(paymentsRepository).findByReceiverZKPO(zkpo, pageable);
    }

    @Test
    void testFindPaymentByZKPO_NoResults() {
        String zkpo = "9876543210";

        Page<Payment> mockPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(paymentsRepository.findByReceiverZKPO(zkpo, pageable)).thenReturn(mockPage);

        ResponseEntity<?> response = paymentController.findPaymentByZKPO(zkpo, pageable.getPageNumber(), pageable.getPageSize());

        assertNotNull(response);
        assertEquals(204, response.getStatusCode().value());

        verify(paymentsRepository).findByReceiverZKPO(zkpo, pageable);
    }

    //get all

    @Test
    void testGetAllPayments_WithResults() {
        UUID paymentId1 = UUID.randomUUID();
        UUID paymentId2 = UUID.randomUUID();

        Payment mockPayment1 = new Payment();
        mockPayment1.setPaymentId(paymentId1);
        mockPayment1.setCard(new Card(UUID.randomUUID(), new Client(),"1234567890111111"));
        mockPayment1.setIban("UA12345678901234567890123456");
        mockPayment1.setMfo("123456");
        mockPayment1.setZkpo("123456789012");
        mockPayment1.setReceiverName("Остап Ступка");
        mockPayment1.setAmount(BigDecimal.valueOf(124));
        mockPayment1.setWithdrawalPeriod(900l);

        Payment mockPayment2 = new Payment();
        mockPayment2.setPaymentId(paymentId2);
        mockPayment2.setCard(new Card(UUID.randomUUID(), new Client(),"1234567890123456"));
        mockPayment2.setIban("UA00045678901234567890123456");
        mockPayment2.setMfo("003456");
        mockPayment2.setZkpo("123456789014");
        mockPayment2.setReceiverName("Богдан Ступка");
        mockPayment2.setAmount(BigDecimal.valueOf(200));
        mockPayment2.setWithdrawalPeriod(200l);

        List<Payment> mockPayments = Arrays.asList(mockPayment1, mockPayment2);

        when(paymentsRepository.findAll()).thenReturn(mockPayments);

        Pageable pageable = PageRequest.of(0, 100);
        Page<Payment> mockPage = new PageImpl<>(mockPayments, pageable, mockPayments.size());

        when(paymentsRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        ResponseEntity<List<PaymentDto>> response = paymentController.getAllPayments(0, 100);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        List<PaymentDto> paymentDtos = response.getBody();
        assertNotNull(paymentDtos);
        assertEquals(2, paymentDtos.size());
        assertEquals(paymentId1, paymentDtos.get(0).id());
        assertEquals(paymentId2, paymentDtos.get(1).id());

        verify(paymentsRepository).findAll(any(Pageable.class));
    }


    @Test
    void testGetAllPayments_NoResults() {
        Pageable pageable = PageRequest.of(0, 100);
        Page<Payment> mockPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(paymentsRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        ResponseEntity<List<PaymentDto>> response = paymentController.getAllPayments(0, 100);

        assertNotNull(response);
        assertEquals(204, response.getStatusCode().value());

        verify(paymentsRepository).findAll(any(Pageable.class));
    }
}
