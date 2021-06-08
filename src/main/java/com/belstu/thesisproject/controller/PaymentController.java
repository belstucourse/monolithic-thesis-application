package com.belstu.thesisproject.controller;

import com.belstu.thesisproject.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/{userId}")
    public void getPaymentUrl(@PathVariable String userId, HttpServletResponse httpServletResponse) throws IOException {
        final String url = paymentService.authorizedPayment(userId);
        httpServletResponse.sendRedirect(url);
    }
}
