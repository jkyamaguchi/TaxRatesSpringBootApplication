package com.example.spring.service;

import java.time.LocalDate;
import java.util.List;

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
	
	private List<TaxRate> retrieveTaxRates(Municipality municipality){
		return taxRateService.findByMunicipality(municipality);
	}
	
	public Double retrieveTaxRate(Municipality municipality, LocalDate date) {
//		Double tax;
//		List<TaxRate> municipalityRates = retrieveTaxRates(municipality);
//		log.info("ZZZZZZZZ");
//		tax = municipalityRates.get(0).getRate();
//		for (TaxRate rate: municipalityRates) {
//			if (date.isAfter(rate.getStartDate()) && date.isBefore(rate.getEndDate())) {
//				tax = rate.getRate();
//			}
//		}
//		log.info("TAX = " + tax);
//		return tax;
		TaxRate vigentTax = taxRateService.findByMunicipalityAndDate(municipality, date)
				.stream()
				.findFirst()
				.orElseThrow();
		return vigentTax.getRate();
	}

}
