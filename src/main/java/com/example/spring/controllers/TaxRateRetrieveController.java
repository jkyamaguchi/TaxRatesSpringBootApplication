package com.example.spring.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.spring.entities.Municipality;
import com.example.spring.service.MunicipalityService;
import com.example.spring.service.TaxRateRetrieveService;

@Controller
@RequestMapping(path = "/taxretrieve/")
public class TaxRateRetrieveController {

	private TaxRateRetrieveService taxRateRetrieveService;

	private MunicipalityService municipalityService;

	private Logger log = LoggerFactory.getLogger(TaxRateRetrieveController.class);

	@Autowired
	public void setTaxRateRetrieveService(TaxRateRetrieveService taxRateRetrieveService) {
		this.taxRateRetrieveService = taxRateRetrieveService;
	}

	@Autowired
	public void setMunicipalityService(MunicipalityService municipalityService) {
		this.municipalityService = municipalityService;
	}

	/*
	 * REST methods do not require @ResponseBody Return a String redirect to html
	 * page in the template folder
	 */
	@GetMapping(path = "/")
	public String index() {
		return "index";
	}

	@GetMapping(path = "/retrieve")
	public String retrieveTaxRate(Model model) {
		model.addAttribute("municipalities", municipalityService.findAll());
		log.info("MUNICIPALITIES " + model.getAttribute("municipalities"));

		return "retrieveTaxRate";
	}

	@GetMapping(path = "/rate")
	public String retrieveRate(HttpServletRequest request, Municipality municipality,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, Model model) {
		log.info("DATE = " + date);

		Double rate = taxRateRetrieveService.retrieveTaxRate(municipality, date);
		log.info("RATE RETRIEVED = " + rate);

		DateTimeFormatter dateFormatter = DateTimeFormatter
				.ofLocalizedDate(FormatStyle.LONG)
				.withLocale(request.getLocale())
				.withZone(ZoneId.systemDefault());
		
		model.addAttribute("municipalities", municipalityService.findAll());
		model.addAttribute("selectedMunicipalityId", municipality.getId());
		model.addAttribute("rate", rate);
		model.addAttribute("date", dateFormatter.format(date));

		return "retrieveTaxRate";
	}

}
