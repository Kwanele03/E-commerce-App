package com.enviro.practice.grad001.kwanelentshele.response;

public class APIResponse {
	
	private String massage;
	private Object data;
	
	public APIResponse(String massage, Object data) {
		super();
		this.massage = massage;
		this.data = data;
	}
	
	public String getMassage() {
		return massage;
	}
	public void setMassage(String massage) {
		this.massage = massage;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
