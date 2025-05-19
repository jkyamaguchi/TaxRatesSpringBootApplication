package com.example.spring.controllers;

public class TaxRateNotFoundException extends ControllerException {
	private static final long serialVersionUID = 1L;
	
	public TaxRateNotFoundException(String message, String viewName) {
        super(message, viewName);
    }
}
