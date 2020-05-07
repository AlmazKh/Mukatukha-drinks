package com.almaz.mukatukha_drinks.utils

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.android.gms.wallet.*

object GooglePayHelper {
    private val SUPPORTED_METHODS = listOf(
        WalletConstants.PAYMENT_METHOD_CARD,
        WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD
    )

    fun createPaymentsClient(activity: Activity): PaymentsClient {
        val walletOptions = Wallet.WalletOptions.Builder()
            .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
            .build()
        return Wallet.getPaymentsClient(activity, walletOptions)
    }

    fun isReadyToPay(client: PaymentsClient): Task<Boolean> {
        val request = IsReadyToPayRequest.newBuilder()
        for (allowedMethod in SUPPORTED_METHODS) {
            request.addAllowedPaymentMethod(allowedMethod)
        }
        return client.isReadyToPay(request.build())
    }

     fun createPaymentDataRequest(): PaymentDataRequest {

        val request = PaymentDataRequest.newBuilder()
            .setTransactionInfo(
                TransactionInfo.newBuilder()
                    .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                    .setTotalPrice("5000")
                    .setCurrencyCode("USD")
                    .build()
            )
            .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
            .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)
            .setCardRequirements(
                CardRequirements.newBuilder()
                    .addAllowedCardNetworks(
                        listOf(
                            WalletConstants.CARD_NETWORK_AMEX,
                            WalletConstants.CARD_NETWORK_JCB,
                            WalletConstants.CARD_NETWORK_DISCOVER,
                            WalletConstants.CARD_NETWORK_MASTERCARD,
                            WalletConstants.CARD_NETWORK_VISA
                        )
                    )
                    .build()
            )
        request.setPaymentMethodTokenizationParameters(createTokenizationParameters())
        return request.build()
    }

    private fun createTokenizationParameters(): PaymentMethodTokenizationParameters =
        PaymentMethodTokenizationParameters.newBuilder().setPaymentMethodTokenizationType(
            WalletConstants.PAYMENT_METHOD_TOKENIZATION_TYPE_PAYMENT_GATEWAY
        )
            .addParameter("gateway", "example")
            .addParameter("gatewayMerchantId", "Almaz")
            .build()
}