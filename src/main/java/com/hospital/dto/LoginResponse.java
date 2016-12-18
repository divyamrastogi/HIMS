package com.hospital.dto;

import com.hospital.constants.HospitalConstants.LoginStatus;

public class LoginResponse {

	private String userId;
	private LoginStatus status;
	private String description;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LoginStatus getStatus() {
		return status;
	}

	public void setStatus(LoginStatus status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
