package com.tkluza.smartcity.smartmobility.business.external.payment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "smart-mobility.http-client")
public record PaymentExternalProperties(
        String paymentApplicationUrl,
        Long networkLatencyMillis
) {

}
