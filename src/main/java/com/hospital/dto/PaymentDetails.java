package com.hospital.dto;

public class PaymentDetails {

	private double total;
	private double discount;
	private double netAmount;
	private String discountDetails;
	private double paidAmount;
	private double balance;
	private String paymentMode;
	private String bank;
	private String cardNumber;

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}

	public String getDiscountDetails() {
		return discountDetails;
	}

	public void setDiscountDetails(String discountDetails) {
		this.discountDetails = discountDetails;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Override
	public String toString() {
		return "PaymentDetails [total=" + total + ", discount=" + discount + ", netAmount=" + netAmount + ", discountDetails=" + discountDetails + ", paidAmount=" + paidAmount + ", balance="
				+ balance + ", paymentMode=" + paymentMode + ", bank=" + bank + ", cardNumber=" + cardNumber + "]";
	}

	
}
