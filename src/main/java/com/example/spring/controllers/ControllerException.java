package com.example.spring.controllers;

import lombok.Getter;

@Getter
abstract public class ControllerException extends RuntimeException {

	private static final long serialVersionUID = 10L;
	
	protected String viewName;

	public ControllerException(String message, String viewName) {
		super(message);
		this.viewName = viewName;
	}
}
