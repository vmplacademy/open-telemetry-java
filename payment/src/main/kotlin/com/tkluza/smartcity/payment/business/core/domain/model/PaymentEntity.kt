package com.tkluza.smartcity.payment.business.core.domain.model

import com.tkluza.smartcity.payment.common.ExternalBusinessKey
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.UUID

@Entity
@Table(name = "t_payment")
class PaymentEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1,

    @ExternalBusinessKey
    @Column(name = "reservation_external_business_key", columnDefinition = "UUID")
    val reservationExternalBusinessKey: UUID,

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    var status: PaymentStatus = PaymentStatus.PROCESSING,

    @Column(name = "date")
    var date: LocalDateTime = now(),

    @Column(name = "price")
    var price: BigDecimal = BigDecimal.ZERO
)