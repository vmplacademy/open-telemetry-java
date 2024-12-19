package com.tkluza.smartcity.smartmobility.business.reservation.domain.service.adapter;

import com.tkluza.smartcity.smartmobility.business.reservation.domain.exception.MaxNumberOfReservationsForAutonomousCarException;
import com.tkluza.smartcity.smartmobility.business.reservation.domain.exception.MaxNumberOfReservationsForUserException;
import com.tkluza.smartcity.smartmobility.business.reservation.domain.gateway.ReservationGateway;
import com.tkluza.smartcity.smartmobility.business.reservation.domain.model.ReservationEntity;
import com.tkluza.smartcity.smartmobility.business.reservation.domain.model.ReservationId;
import com.tkluza.smartcity.smartmobility.business.reservation.domain.model.ReservationStatus;
import com.tkluza.smartcity.smartmobility.business.reservation.domain.repository.ReservationRepository;
import com.tkluza.smartcity.smartmobility.business.reservation.domain.service.ReservationService;
import com.tkluza.smartcity.smartmobility.business.reservation.dto.command.CancelReservationCommand;
import com.tkluza.smartcity.smartmobility.business.reservation.dto.command.ConfirmReservationCommand;
import com.tkluza.smartcity.smartmobility.business.reservation.dto.command.CreateReservationCommand;
import com.tkluza.smartcity.smartmobility.business.reservation.dto.event.ReservationCreatedEvent;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

@Slf4j
public class ReservationServiceAdapter implements ReservationService {

    private static final int MAX_NUMBER_OF_RESERVATIONS_PER_USER = 2;
    private static final int MAX_NUMBER_OF_RESERVATIONS_PER_AUTONOMOUS_CAR = 4;
    private static final Random random = new Random();

    private final ReservationGateway reservationGateway;
    private final ReservationRepository reservationRepository;

    private final Counter confirmedReservationCounter;
    private final Counter cancelledReservationCounter;

    public ReservationServiceAdapter(MeterRegistry meterRegistry,
                                     ReservationGateway reservationGateway,
                                     ReservationRepository reservationRepository) {
        this.reservationGateway = reservationGateway;
        this.reservationRepository = reservationRepository;

        this.confirmedReservationCounter = Counter.builder("business_confirmed_reservations_total")
                .description("Total number of confirmed reservations")
                .register(meterRegistry);
        this.cancelledReservationCounter = Counter.builder("business_cancelled_reservations_total")
                .description("Total number of cancelled reservations")
                .tags()
                .register(meterRegistry);
    }

    @Override
    public void create(CreateReservationCommand command) {

        validateCreation(command);

        UUID reservationBusinessKey = UUID.randomUUID();

        ReservationEntity entity = ReservationEntity.builder()
                .id(ReservationId.builder()
                        .autonomousCarId(command.autonomousCarId())
                        .userId(command.userId())
                        .reservationDate(LocalDateTime.now())
                        .build())
                .businessKey(reservationBusinessKey)
                .status(ReservationStatus.CREATED)
                .build();

        reservationRepository.save(entity);

        reservationGateway.publishEvent(
                ReservationCreatedEvent.builder()
                        .reservationBusinessKey(reservationBusinessKey)
                        .build()
        );
    }

    private void validateCreation(CreateReservationCommand command) {
        requireNonNull(command);
        requireNonNull(command);
        requireNonNull(command);

        if (reservationRepository.findAllByUserId(command.userId()).size() >= MAX_NUMBER_OF_RESERVATIONS_PER_USER) {
            throw new MaxNumberOfReservationsForUserException(command.userId());
        }

        if (reservationRepository.findAllByAutonomousCarId(command.autonomousCarId()).size() >= MAX_NUMBER_OF_RESERVATIONS_PER_AUTONOMOUS_CAR) {
            throw new MaxNumberOfReservationsForAutonomousCarException(command.autonomousCarId());
        }
    }

    @Override
    public void createRandom() {
        long randomUserId = random.nextLong(reservationGateway.findAllUsers().size()) + 1;
        long randomAutonomousCar = random.nextLong(reservationGateway.findAllAutonomousCars().size()) + 1;

        create(
                CreateReservationCommand.builder()
                        .userId(randomUserId)
                        .autonomousCarId(randomAutonomousCar)
                        .build()
        );
    }

    @Override
    public void confirm(ConfirmReservationCommand command) {
        validateConfirmation(command);

        update(command.reservationExternalBusinessKey(),
                reservationEntity -> {
                    reservationEntity.setStatus(ReservationStatus.CONFIRMED);
                    reservationEntity.setPrice(command.price());
                }
        );

        log.info("Reservation: {} has been confirmed", command.reservationExternalBusinessKey());
        confirmedReservationCounter.increment();
    }

    private void validateConfirmation(ConfirmReservationCommand command) {
        requireNonNull(command);
        requireNonNull(command.price());
        requireNonNull(command.reservationExternalBusinessKey());
    }

    private void update(UUID businessKey, Consumer<ReservationEntity> consumer) {
        reservationRepository.findByBusinessKey(businessKey)
                .ifPresentOrElse(
                        consumer,
                        () -> {
                            throw new EntityNotFoundException();
                        }
                );
    }

    @Override
    public void cancel(CancelReservationCommand command) {
        validateCancellation(command);

        update(command.reservationExternalBusinessKey(),
                reservationEntity -> reservationEntity.setStatus(ReservationStatus.CANCELLED)
        );

        log.warn("Reservation: {} has been cancelled", command.reservationExternalBusinessKey());
        cancelledReservationCounter.increment();
    }

    private void validateCancellation(CancelReservationCommand command) {
        requireNonNull(command);
        requireNonNull(command.reservationExternalBusinessKey());
    }
}
