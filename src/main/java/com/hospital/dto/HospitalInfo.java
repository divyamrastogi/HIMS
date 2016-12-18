package com.hospital.dto;

public class HospitalInfo {

	private int turn;
	private String sourceType;
	private String sourceId;
	private String category;

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "HospitalInfo [turn=" + turn + ", sourceType=" + sourceType + ", sourceId=" + sourceId + ", category=" + category + "]";
	}

}
