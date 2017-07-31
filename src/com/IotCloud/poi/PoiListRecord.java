package com.IotCloud.poi;

import java.util.List;

public class PoiListRecord<T> {
	List<T> recordList;
	String message;
	
	public PoiListRecord() {
		
	}
	
	public PoiListRecord(List<T> recordList, String message) {
		this.recordList = recordList;
		this.message = message;
	}
	
	public List<T> getRecordList(){
		return recordList;
	}
	
	public void setRecordList(List<T> recordList) {
		this.recordList = recordList;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
