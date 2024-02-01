package com.ms.entity;

import com.ms.user.domain.PaymentMethod;
import com.ms.user.domain.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {
	
	private PaymentMethod paymentMethod;
	private PaymentStatus status;
	private String paymentId;
	private String razopayPaymentLinkId;
	private String razopayPaymentLinkReferenceId;
	private String razopayPaymentLinkStatus;
	private String razopayPaymentId;
	

}
