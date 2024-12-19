package com.tkluza.smartcity.smartmobility.business.external.payment;

import com.tkluza.smartcity.smartmobility.business.external.payment.http.PaymentExternalHttpClient;
import com.tkluza.smartcity.smartmobility.business.reservation.dto.event.ReservationCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class PaymentExternalFacadeAdapter implements PaymentExternalFacade {

    private final PaymentExternalHttpClient httpClient;

    @Override
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void processEvent(ReservationCreatedEvent event) {
        log.info("Reservation: {} has been created", event.reservationBusinessKey());
        httpClient.sendRequest(event);
    }
}
