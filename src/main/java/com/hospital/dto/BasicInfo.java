package com.hospital.dto;

import java.util.Date;

public class BasicInfo {

	private String title;
	private String name;
	private String sex;
	private String relation;
	private String relativeName;
	private String dob;

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getRelativeName() {
		return relativeName;
	}

	public void setRelativeName(String relativeName) {
		this.relativeName = relativeName;
	}

	@Override
	public String toString() {
		return "BasicInfo [title=" + title + ", name=" + name + ", sex=" + sex + ", relation=" + relation + ", relativeName=" + relativeName + "]";
	}

}
