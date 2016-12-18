package com.hospital.service;

import com.hospital.dto.LoginResponse;

public interface LoginService {

	public LoginResponse doLogin(String username, String password);

	public void populatedb() throws Exception;
}
