package com.hospital.dto;

public class ConsultantDepartment {

	private String department;
	private String doctorId;

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	@Override
	public String toString() {
		return "ConsultantDepartment [department=" + department + ", doctorId=" + doctorId + "]";
	}

	
}
