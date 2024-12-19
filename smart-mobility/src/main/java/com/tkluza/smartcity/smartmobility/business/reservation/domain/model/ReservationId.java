package com.tkluza.smartcity.smartmobility.business.reservation.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationId implements Serializable {

    @NotNull
    @Column(name = "autonomous_car_id")
    private Long autonomousCarId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "reservation_date")
    private LocalDateTime reservationDate;
}
