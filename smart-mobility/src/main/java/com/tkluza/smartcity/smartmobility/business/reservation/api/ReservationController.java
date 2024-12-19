package com.tkluza.smartcity.smartmobility.business.reservation.api;

import com.tkluza.smartcity.smartmobility.business.reservation.dto.command.CancelReservationCommand;
import com.tkluza.smartcity.smartmobility.business.reservation.dto.command.ConfirmReservationCommand;
import com.tkluza.smartcity.smartmobility.business.reservation.dto.command.CreateReservationCommand;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/reservations")
public interface ReservationController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    void createReservation(CreateReservationCommand command);

    @PostMapping(path = "/random", consumes = MediaType.APPLICATION_JSON_VALUE)
    void createRandomReservation();

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    void confirmReservation(ConfirmReservationCommand command);

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    void cancelReservation(CancelReservationCommand command);
}
