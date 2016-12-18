package com.hospital.dto;

public class RegisterRequest {

	private BasicInfo basicInfo;
	private Address address;
	private HospitalInfo hospitalInfo;
	private ConsultantDepartment consultantDepartment;
	private PaymentDetails paymentDetails;

	public BasicInfo getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(BasicInfo basicInfo) {
		this.basicInfo = basicInfo;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public HospitalInfo getHospitalInfo() {
		return hospitalInfo;
	}

	public void setHospitalInfo(HospitalInfo hospitalInfo) {
		this.hospitalInfo = hospitalInfo;
	}

	public ConsultantDepartment getConsultantDepartment() {
		return consultantDepartment;
	}

	public void setConsultantDepartment(ConsultantDepartment consultantDepartment) {
		this.consultantDepartment = consultantDepartment;
	}

	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	@Override
	public String toString() {
		return "RegisterRequest [basicInfo=" + basicInfo.toString() + "\n" + ", address=" + address.toString() + ", \nhospitalInfo=" + hospitalInfo.toString() + ", \nconsultantDepartment=" + consultantDepartment.toString() + ", \npaymentDetails="
				+ paymentDetails.toString() + "]";
	}

	
}
