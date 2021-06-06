package com.belstu.thesisproject.config;

import lombok.NoArgsConstructor;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PaymentParams {
    public static final String SERVICE_NAME = "PSYCHO_HELP";
    public static final String SUB_TOTAL = format("%.3f", 15.0);
    public static final String SHIPPING = format("%.3f", 5.0);
    public static final String TAX = format("%.3f", 10.0);
    public static final String TOTAL = format("%.3f", 30.0);
}
