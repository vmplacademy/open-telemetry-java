package com.tkluza.smartcity.smartmobility.business.autonomouscar.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_autonomous_car")
public class AutonomousCarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model")
    private String model;

    @NotNull
    @Column(name = "manufacturer")
    @Enumerated(value = EnumType.STRING)
    private Manufacturer manufacturer;

    @NotNull
    @Column(name = "number_of_seats")
    private short numberOfSeats;

    @NotNull
    @Column(name = "registration_plate")
    private String registrationPlate;

    @NotNull
    @Column(name = "battery_level")
    private double batteryLevel;
}