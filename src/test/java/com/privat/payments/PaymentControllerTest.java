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
import org.springframework.http.ResponseEntity;


import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentControllerTest {

    private PaymentsRepository paymentsRepository;
    private CardRepository cardRepository;
    private PaymentController paymentController;

    @BeforeEach
    public void setUp() {
        paymentsRepository = mock(PaymentsRepository.class);
        cardRepository = mock(CardRepository.class);
        paymentController = new PaymentController(paymentsRepository, cardRepository);
    }

    //savePayment

    @Test
    void testSavePayment() {
        UUID cardId = UUID.randomUUID();
        UUID paymentId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(100.00);

        PaymentCreateDto paymentCreateDto = new PaymentCreateDto(cardId, "UA1234567890",
                "300335", "1234567890", "John Doe", amount, 1000l);

        Card mockCard = new Card();
        mockCard.setCardId(cardId);

        Payment mockPayment = new Payment();
        mockPayment.setPaymentId(paymentId);
        mockPayment.setCard(mockCard);
        mockPayment.setIban(paymentCreateDto.IBAN());
        mockPayment.setMfo(paymentCreateDto.MFO());
        mockPayment.setZkpo(paymentCreateDto.ZKPO());
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
                "123456", "9876543210", "Jane Doe", amount, 1000l);

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
        Payment mockPayment = new Payment();
        mockPayment.setPaymentId(UUID.randomUUID());

        when(paymentsRepository.findByClientITN(itn)).thenReturn(Collections.singletonList(mockPayment));

        ResponseEntity<?> response = paymentController.findPaymentByITN(itn);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(((List<?>) response.getBody()).size() > 0);

        verify(paymentsRepository).findByClientITN(itn);
    }

    @Test
    void testFindPaymentByITN_NoPaymentsFound() {
        String itn = "1234567890";

        when(paymentsRepository.findByClientITN(itn)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = paymentController.findPaymentByITN(itn);

        assertNotNull(response);
        assertEquals(404, response.getStatusCode().value());
        assertEquals("No payments found for ITN: " + itn, response.getBody());

        verify(paymentsRepository).findByClientITN(itn);
    }

    //findbyZKPO

    @Test
    void testFindPaymentByZKPO_WithResults() {
        String zkpo = "1234567890";
        Payment mockPayment = new Payment();
        mockPayment.setPaymentId(UUID.randomUUID());
        mockPayment.setZkpo(zkpo);

        when(paymentsRepository.findByReceiverZKPO(zkpo)).thenReturn(Collections.singletonList(mockPayment));

        ResponseEntity<?> response = paymentController.findPaymentByZKPO(zkpo);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertInstanceOf(List.class, response.getBody());
        List<Payment> payments = (List<Payment>) response.getBody();
        assertEquals(1, payments.size());
        assertEquals(zkpo, payments.getFirst().getZkpo());

        verify(paymentsRepository).findByReceiverZKPO(zkpo);
    }

    @Test
    void testFindPaymentByZKPO_NoResults() {
        String zkpo = "9876543210";

        when(paymentsRepository.findByReceiverZKPO(zkpo)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = paymentController.findPaymentByZKPO(zkpo);

        assertNotNull(response);
        assertEquals(404, response.getStatusCode().value());
        assertEquals("No payments found for ZKPO: " + zkpo, response.getBody());

        verify(paymentsRepository).findByReceiverZKPO(zkpo);
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

        ResponseEntity<List<PaymentDto>> response = paymentController.getAllPayments();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        List<PaymentDto> paymentDtos = response.getBody();
        assertNotNull(paymentDtos);
        assertEquals(2, paymentDtos.size());
        assertEquals(paymentId1, paymentDtos.get(0).id());
        assertEquals(paymentId2, paymentDtos.get(1).id());

        verify(paymentsRepository).findAll();
    }

    @Test
    void testGetAllPayments_NoResults() {
        when(paymentsRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<PaymentDto>> response = paymentController.getAllPayments();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        List<PaymentDto> paymentDtos = response.getBody();
        assertNotNull(paymentDtos);
        assertTrue(paymentDtos.isEmpty());

        verify(paymentsRepository).findAll();
    }
}
