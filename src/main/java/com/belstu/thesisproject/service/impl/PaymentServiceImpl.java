package com.belstu.thesisproject.service.impl;

import com.belstu.thesisproject.domain.user.User;
import com.belstu.thesisproject.exception.NotFoundException;
import com.belstu.thesisproject.service.PaymentService;
import com.belstu.thesisproject.service.UserService;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.belstu.thesisproject.config.PaymentParams.SERVICE_NAME;
import static com.belstu.thesisproject.config.PaymentParams.SHIPPING;
import static com.belstu.thesisproject.config.PaymentParams.SUB_TOTAL;
import static com.belstu.thesisproject.config.PaymentParams.TAX;
import static com.belstu.thesisproject.config.PaymentParams.TOTAL;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final String CLIENT_ID = "AZIt_V_ZqxLwnRM42s9C1rQ9egmUyETOLpoPQbbTwZZe-G0FvJ13G7mQ1eFIC6Qx_wJbe82dlpX4AfcW";
    private static final String CLIENT_SECRET = "EDJUEqExBwBgMh5IHEs9mH8bbebU4UqqenFE_n_xz_LJOTJKORg9BhyBnoYX8kLys7qiEos9F2QtFxM1";
    private static final String MODE = "sandbox";
    private static final String PAYMENT_METHOD = "paypal";

    private final UserService userService;

    @SneakyThrows
    @Override
    public String authorizedPayment(String userId) {
        Map<String, Object> response = new HashMap<String, Object>();
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(TOTAL);
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:4200/catalog");
        redirectUrls.setReturnUrl("http://localhost:4200/order");
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment;
        String redirectUrl = "";
        try {

            APIContext context = new APIContext(CLIENT_ID, CLIENT_SECRET, "sandbox");
            createdPayment = payment.create(context);
            if(createdPayment!=null){
                List<Links> links = createdPayment.getLinks();
                for (Links link:links) {
                    if(link.getRel().equals("approval_url")){
                        redirectUrl = link.getHref();
                        break;
                    }
                }
                response.put("status", "success");
                response.put("redirect_url", redirectUrl);
            }
        } catch (PayPalRESTException e) {
            System.out.println("Error happened during payment creation!");
        }

        return redirectUrl;
    }

    private String getApprovalLink(Payment approvedPayment) {
        return approvedPayment.getLinks().stream()
                .filter(link -> link.getRel().equalsIgnoreCase("approval_link"))
                .findFirst().orElseThrow(() -> new NotFoundException("Illegal payment")).getHref();
    }

    private RedirectUrls prepareRedirectUrl() {
        final RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:4200/catalog");
        redirectUrls.setCancelUrl("http://localhost:4200/order");
        return redirectUrls;
    }

    private List<Transaction> getTransactionInformation() {
        final Details details = new Details();
        details.setShipping(SHIPPING);
        details.setSubtotal(SUB_TOTAL);
        details.setTax(TAX);

        final Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(TOTAL);
        amount.setDetails(details);

        final Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(SERVICE_NAME);

        final ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        final Item item = new Item();
        item.setCurrency("USD")
                .setName(SERVICE_NAME)
                .setPrice(SUB_TOTAL)
                .setTax(TAX)
                .setQuantity("1");

        items.add(item);

        itemList.setItems(items);
        transaction.setItemList(itemList);

        final List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        return transactions;
    }

    private Payer preparePayerInfo(User user) {
        final Payer payer = new Payer();
        payer.setPaymentMethod(PAYMENT_METHOD);
        final PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail());
        payer.setPayerInfo(payerInfo);
        return payer;
    }
}
