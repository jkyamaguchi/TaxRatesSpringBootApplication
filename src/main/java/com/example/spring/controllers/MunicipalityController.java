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

import com.example.spring.entities.Municipality;
import com.example.spring.service.MunicipalityService;

@Controller
@RequestMapping(path="/municipalities/")
public class MunicipalityController {
	
	private MunicipalityService municipalityService;
	
	@Autowired
	public void setMunicipalityService(MunicipalityService municipalityService) {
		this.municipalityService = municipalityService;
	}
	
	/*
	 * JSON methods require @ResponseBody
	 */
	@GetMapping(path="{id}")
	@ResponseBody
	public Optional<Municipality> getMunicipalityName(@PathVariable(name="id") Long id) {
		return municipalityService.getMunicipality(id); 
	}
	
	@GetMapping(path="/list")
	@ResponseBody
	public List<Municipality> getMunicipalities() {
		return municipalityService.findAll();
	}

	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Municipality saveMunicipality(@RequestBody Municipality municipalityToSave) {
		return municipalityService.saveMunicipality(municipalityToSave);
	}
	
	@PutMapping(path="{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Municipality updateMunicipality(@RequestBody Municipality municipalityToUpdate, @PathVariable(name="id") Long id) {
		return municipalityService.updateMunicipality(municipalityToUpdate, id);
	}
	
	@DeleteMapping(path="{id}")
	@ResponseBody
	public void deleteMunicipality(@PathVariable(name="id") Long id) {
		municipalityService.deleteMunicipality(id);
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
	public String addMunicipality(Model model){
		model.addAttribute("municipality", new Municipality());
		return "editMunicipality";
	}
	
	@PostMapping(path="/store")
	public String storeMunicipality(Municipality municipality) {
		municipalityService.saveMunicipality(municipality);
		return "redirect:/";
	}

	
	@GetMapping(path="/findAll")
	public String getAllMunicipalities(Model model) {
		model.addAttribute("municipalities", municipalityService.findAll());
		return "listMunicipalities";
	}
	
	/*
	 * The updateMunicipality method calls getMunicipality from Service 
	 * because it keeps the original reference to the object.
	 */
	@GetMapping(path="/update/{id}")
	public String updateMunicipality(Model model, @PathVariable(name="id") Long id) {
		model.addAttribute("municipality", municipalityService.getMunicipality(id));
		return "editMunicipality";
	}
	
	@GetMapping(path="/delete/{id}")
	public String removeMunicipality(@PathVariable(value="id") Long id) {
		municipalityService.deleteMunicipality(id);
		return "redirect:/municipalities/findAll";
	}
}
