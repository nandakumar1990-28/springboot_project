package com.myproject.myproject.error;

public class DepartmentNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;
	public DepartmentNotFoundException(String message)
	{
		super(message);
		this.message = message;		
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
