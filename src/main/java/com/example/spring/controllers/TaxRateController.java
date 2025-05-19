package com.example.spring.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.spring.entities.TaxRate;
import com.example.spring.services.MunicipalityService;
import com.example.spring.services.TaxRateService;

@Controller
@RequestMapping(path="/taxrates/")
public class TaxRateController {
	
	private TaxRateService taxRateService;
	
	private MunicipalityService municipalityService;
	
	@Autowired
	public void setTaxRateService(TaxRateService taxRateService) {
		this.taxRateService = taxRateService;
	}
	
	@Autowired
	public void setMunicipalityService(MunicipalityService municipalityService) {
		this.municipalityService = municipalityService;
	}
	
	/*
	 * JSON methods require @ResponseBody
	 */
	@GetMapping(path="{id}")
	@ResponseBody
	public Optional<TaxRate> getTaxRate(@PathVariable(name="id") Long id) {
		return taxRateService.getTaxRate(id); 
	}
	
	@GetMapping(path="/list")
	@ResponseBody
	public List<TaxRate> getTaxes() {
		return taxRateService.findAll();
	}

	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TaxRate saveTaxRate(@RequestBody TaxRate taxRateToSave) {
		return taxRateService.saveTaxRate(taxRateToSave);
	}
	
	@PutMapping(path="{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TaxRate updateTaxRate(@RequestBody TaxRate taxRateToUpdate, @PathVariable(name="id") Long id) {
		return taxRateService.updateTaxRate(taxRateToUpdate, id);
	}
	
	@DeleteMapping(path="{id}")
	@ResponseBody
	public void deleteTaxRate(@PathVariable(name="id") Long id) {
		taxRateService.deleteTaxRate(id);
	}
	
	/*
	 * REST methods do not require @ResponseBody
	 * Return a String redirect to html page in the template folder
	 */
	@GetMapping(path="/")
	public String index() {
		return "index";
	}
	
	@GetMapping(path="/add")
	public String addTaxRate(Model model){
		model.addAttribute("taxrate", new TaxRate());
		model.addAttribute("municipalities", municipalityService.findAll());
		return "editTaxRate";
	}
	
	@PostMapping(path="/store")
	public String storeTaxRate(TaxRate taxRate) {
		taxRateService.saveTaxRate(taxRate);
		return "redirect:/";
	}

	
	@GetMapping(path="/findAll")
	public String getAllTaxes(Model model) {
		model.addAttribute("taxrates", taxRateService.findAll());
		return "listTaxRates";
	}
	
	@GetMapping(path="/update/{id}")
	public String updateTaxRate(Model model, @PathVariable(name="id") Long id) {
		model.addAttribute("taxrate", taxRateService.getTaxRate(id));
		model.addAttribute("municipalities", municipalityService.findAll());
		return "editTaxRate";
	}
	
	@GetMapping(path="/delete/{id}")
	public String removeTaxRate(@PathVariable(value="id") Long id) {
		taxRateService.deleteTaxRate(id);
		return "redirect:/taxrates/findAll";
	}
}
