package com.IotCloud.poi;

public class PoiRecord<T> {
	private T record;
	private String message;

	public PoiRecord() {

	}

	public PoiRecord(T record, String message) {
		this.record = record;
		this.message = message;
	}

	public T getRecord() {
		return record;
	}

	public void setRecord(T record) {
		this.record = record;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
