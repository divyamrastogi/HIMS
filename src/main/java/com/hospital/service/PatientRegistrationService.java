package com.hospital.service;

import java.util.List;

import com.hospital.dto.Authorisation;
import com.hospital.dto.Bank;
import com.hospital.dto.City;
import com.hospital.dto.Department;
import com.hospital.dto.Doctor;
import com.hospital.dto.RegisterRequest;
import com.hospital.dto.RegisterResponse;
import com.hospital.dto.Source;

public interface PatientRegistrationService {

	public List<Department> getDepartments();

	public List<Doctor> getDoctors(String departmentId);

	public List<Doctor> getReferralDoctors();

	public List<String> getOrganizations();
	
	public List<Bank> getBanks();

	public List<Source> getSource(String source);

	public List<City> getCities(String prefix);
	
	public List<Authorisation> getAuthorisations();
	
	public RegisterResponse register(RegisterRequest request);

}
