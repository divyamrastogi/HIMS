package com.hospital.service;

import java.util.List;

import com.hospital.dao.HospitalDAO;
import com.hospital.dto.Authorisation;
import com.hospital.dto.Bank;
import com.hospital.dto.City;
import com.hospital.dto.Department;
import com.hospital.dto.Doctor;
import com.hospital.dto.RegisterRequest;
import com.hospital.dto.RegisterResponse;
import com.hospital.dto.Source;
import com.hospital.factory.ObjectFactory;

public class PatientRegistrationServiceImpl implements PatientRegistrationService {

	private HospitalDAO hospitalDAO;

	public PatientRegistrationServiceImpl(ObjectFactory objectFactory) {
		this.hospitalDAO = objectFactory.getHospitalDao();
	}

	@Override
	public List<Department> getDepartments() {
		return hospitalDAO.getDepartments();
	}

	@Override
	public List<Doctor> getDoctors(String departmentId) {
		return hospitalDAO.getDoctors(departmentId);
	}

	@Override
	public List<Doctor> getReferralDoctors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bank> getBanks() {
		return hospitalDAO.getBanks();
	}

	@Override
	public List<String> getOrganizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Source> getSource(String source) {
		return hospitalDAO.getSources(source);
	}

	public List<City> getCities(String prefix) {
		return hospitalDAO.getCities(prefix);
	}

	@Override
	public List<Authorisation> getAuthorisations() {
		return hospitalDAO.getAuthorisations();
	}

	@Override
	public RegisterResponse register(RegisterRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
