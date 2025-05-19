package com.example.spring.services;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.entities.Municipality;
import com.example.spring.entities.TaxRate;

@Service
public class TaxRateRetrieveService {
	
	private TaxRateService taxRateService;
	
	@Autowired
	public void setTaxRateService(TaxRateService taxRateService) {
		this.taxRateService = taxRateService;
	}

	private Logger log = LoggerFactory.getLogger(TaxRateRetrieveService.class);
		
	public Double retrieveTaxRate(Municipality municipality, LocalDate date) {//throws TaxRateNotFoundException {
		TaxRate vigentTax = taxRateService.findByMunicipalityAndDate(municipality, date)
				.stream()
				.findFirst()
				.orElseThrow();
		return vigentTax.getRate();
	}

}
