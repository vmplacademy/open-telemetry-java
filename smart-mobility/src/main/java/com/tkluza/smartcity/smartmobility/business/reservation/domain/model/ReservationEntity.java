package com.tkluza.smartcity.smartmobility.business.reservation.domain.model;

import com.tkluza.smartcity.smartmobility.business.autonomouscar.domain.model.AutonomousCarEntity;
import com.tkluza.smartcity.smartmobility.business.user.domain.model.UserEntity;
import com.tkluza.smartcity.smartmobility.common.BusinessKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_reservation", uniqueConstraints = {
        @UniqueConstraint(
                name = "uc_reservation_business_key",
                columnNames = {"business_key"})
})
public class ReservationEntity {

    @EmbeddedId
    private ReservationId id;

    @NotNull
    @BusinessKey
    @Column(name = "business_key", columnDefinition = "UUID")
    private UUID businessKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autonomous_car_id", insertable = false, updatable = false)
    private AutonomousCarEntity autonomousCarEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity userEntity;

    @NotNull
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private ReservationStatus status;

    @Column(name = "price")
    private BigDecimal price;
}
