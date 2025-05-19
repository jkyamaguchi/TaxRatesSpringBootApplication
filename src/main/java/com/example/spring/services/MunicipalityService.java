package com.example.spring.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.entities.Municipality;
import com.example.spring.repositories.MunicipalityRepository;

@Service
public class MunicipalityService {
	
	private MunicipalityRepository municipalityRepository;
	
	@Autowired
	public void setMunicipalityRepository(MunicipalityRepository municipalityRepository) {
		this.municipalityRepository = municipalityRepository;
	}
	
	private Logger log = LoggerFactory.getLogger(MunicipalityService.class);
	
	public Optional<Municipality> getMunicipality(Long id) {
		return municipalityRepository.findById(id);
	}
	
	public Municipality saveMunicipality(Municipality municipalityToSave) {
		return municipalityRepository.save(municipalityToSave);
	}
	
	public Municipality updateMunicipality(Municipality municipalityToUpdate, Long id) {
		//find the object in the repository
		Optional<Municipality> foundMunicipality = municipalityRepository.findById(id);
		
		if (foundMunicipality.isPresent()) {
			Municipality update = foundMunicipality.get();
			update.setName(municipalityToUpdate.getName());
			
			return municipalityRepository.save(update);
		}
		else {
			log.info("No municipality found with given id");
			return municipalityToUpdate;
		}
	}
	
	public void deleteMunicipality(Long id) {
		municipalityRepository.deleteById(id);
	}
	
	public List<Municipality> findAll(){
		return municipalityRepository.findAll();
	}
}
