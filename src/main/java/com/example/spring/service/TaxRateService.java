package com.example.spring.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.entities.Municipality;
import com.example.spring.entities.TaxRate;
import com.example.spring.repository.TaxRateRepository;

@Service
public class TaxRateService {
	
	private TaxRateRepository taxRateRepository;
	
	@Autowired
	public void setTaxRateRepository(TaxRateRepository taxRateRepository) {
		this.taxRateRepository = taxRateRepository;
	}

	private Logger log = LoggerFactory.getLogger(TaxRateService.class);

	public Optional<TaxRate> getTaxRate(Long id) {
		return taxRateRepository.findById(id);
	}
	
	public TaxRate saveTaxRate(TaxRate taxRateToSave) {
		return taxRateRepository.save(taxRateToSave);
	}
	
	public TaxRate updateTaxRate(TaxRate taxRateToUpdate, Long id) {
		//find the object in the repository
		Optional<TaxRate> foundTaxRate= taxRateRepository.findById(id);
		
		if (foundTaxRate.isPresent()) {
			TaxRate update = foundTaxRate.get();
			update.setPeriodicity(taxRateToUpdate.getPeriodicity());
			update.setStartDate(taxRateToUpdate.getStartDate());
			update.setEndDate(taxRateToUpdate.getEndDate());
			update.setRate(taxRateToUpdate.getRate());
			update.setMunicipality(taxRateToUpdate.getMunicipality());

			return taxRateRepository.save(update);
		}
		else {
			log.info("No taxRateRepository found with given id");
			return taxRateToUpdate;
		}
	}
	
	public void deleteTaxRate(Long id) {
		taxRateRepository.deleteById(id);
	}
	
	public List<TaxRate> findAll(){
		return taxRateRepository.findAll();
	}
	
	public List<TaxRate> findByMunicipality(Municipality municipality) {
		log.info("MMMMMMMMMMM " + municipality.getId());
		
		return taxRateRepository.findByMunicipality(municipality);
	}
	
	public List<TaxRate> findByMunicipalityAndDate(Municipality municipality, LocalDate date) {
		log.info("MUNICIPALITY ID " + municipality.getId());
		
		return taxRateRepository.findByMunicipalityAndDate(municipality, date);
	}
}
