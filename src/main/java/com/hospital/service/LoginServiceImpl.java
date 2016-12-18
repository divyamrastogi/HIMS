package com.hospital.service;

import com.hospital.constants.HospitalConstants.LoginStatus;
import com.hospital.dao.HospitalDAO;
import com.hospital.dto.LoginResponse;
import com.hospital.factory.ObjectFactory;

public class LoginServiceImpl implements LoginService {

	private HospitalDAO hospitalDAO;
	private ObjectFactory objectFactory;

	public LoginServiceImpl(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
		this.hospitalDAO = objectFactory.getHospitalDao();
	}

	public void populatedb() throws Exception {
		hospitalDAO.populatedb();
	}
	@Override
	public LoginResponse doLogin(String username, String password) {
		LoginResponse loginResponse = new LoginResponse();
		String dbPassword = hospitalDAO.getEncryptedPassword(username);
		String userId = hospitalDAO.getUserId(username);
		if(dbPassword == null) {
			loginResponse.setStatus(LoginStatus.FAILURE);
			loginResponse.setDescription("Invalid username !!");
		} else if(!password.equals(dbPassword)) {
			loginResponse.setStatus(LoginStatus.FAILURE);
			loginResponse.setDescription("Invalid password for username : "+ username+" !!");
		} else {
			loginResponse.setStatus(LoginStatus.SUCCESS);
			loginResponse.setDescription("Login successful !!");
			loginResponse.setUserId(userId);
		}
		return loginResponse;
	}
}
