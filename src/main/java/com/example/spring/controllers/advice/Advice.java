package com.example.spring.controllers.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.spring.controllers.TaxRateNotFoundException;
import com.example.spring.services.MunicipalityService;

@ControllerAdvice
public class Advice {
	
	@Autowired
    private MunicipalityService municipalityService;
	
	@ExceptionHandler(TaxRateNotFoundException.class)
    public String handleTaxRateNotFound(TaxRateNotFoundException ex, Model model) {
		model.addAttribute("municipalities", municipalityService.findAll());
        model.addAttribute("errorMessage", ex.getMessage());
        return ex.getViewName();
    }

}
