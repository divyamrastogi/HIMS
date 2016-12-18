package com.hospital.dto;

/**
 * Doctor Pojo.
 *
 * @author Shivam Khare
 */
public class Doctor {

	private String name;
	private String id;
	private double fee;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String string) {
		this.id = string;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

}
