package com.example.spring.controllers;

abstract public class ControllerException extends RuntimeException {

	private static final long serialVersionUID = 10L;
	
	protected String viewName;

	public ControllerException(String message, String viewName) {
		super(message);
		this.viewName = viewName;
	}

	public String getViewName() {
		return viewName;
	}

}
