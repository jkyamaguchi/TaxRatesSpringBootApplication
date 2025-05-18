package com.example.spring.boot;

import java.time.LocalDate;
import java.time.Month;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.spring.entities.Municipality;
import com.example.spring.entities.TaxRate;
import com.example.spring.repository.MunicipalityRepository;
import com.example.spring.repository.TaxRateRepository;

@Component
public class DataLoader implements CommandLineRunner{

	private Logger log = LoggerFactory.getLogger(DataLoader.class);
	
	private MunicipalityRepository municipalityRepository;
	private TaxRateRepository taxRateRepository;

	@Autowired
	public void setMunicipalityRepository(MunicipalityRepository municipalityRepository) {
		this.municipalityRepository = municipalityRepository;
	}

	@Autowired
	public void setTaxRateRepository(TaxRateRepository taxRateRepository) {
		this.taxRateRepository = taxRateRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		
		if (municipalityRepository.count()<1) {
			
			Municipality municipality1 = new Municipality();
			municipality1.setName("Copenhagen");
			municipalityRepository.save(municipality1);
			
			TaxRate yearly = new TaxRate();
			yearly.setMunicipality(municipality1);
			yearly.setPeriodicity("yearly");
			yearly.setPriority(3);
			yearly.setStartDate(LocalDate.of(2025, Month.JANUARY, 1));
	        yearly.setEndDate(LocalDate.of(2025, Month.DECEMBER, 31));
			yearly.setRate(0.2);
			taxRateRepository.save(yearly);

			TaxRate monthly = new TaxRate();
			monthly.setMunicipality(municipality1);
			monthly.setPeriodicity("monthly");
			monthly.setPriority(2);
			monthly.setStartDate(LocalDate.of(2025, Month.MAY, 1));
			monthly.setEndDate(LocalDate.of(2025, Month.MAY, 31));
			monthly.setRate(0.4);
			taxRateRepository.save(monthly);

			TaxRate dailyJan = new TaxRate();
			dailyJan.setMunicipality(municipality1);
			dailyJan.setPeriodicity("daily");
			dailyJan.setPriority(1);
			dailyJan.setStartDate(LocalDate.of(2025, Month.JANUARY, 1));
			dailyJan.setEndDate(LocalDate.of(2025, Month.JANUARY, 1));
			dailyJan.setRate(0.1);
			taxRateRepository.save(dailyJan);

			TaxRate dailyDez = new TaxRate();
			dailyDez.setMunicipality(municipality1);
			dailyDez.setPeriodicity("daily");
			dailyDez.setPriority(1);
			dailyDez.setStartDate(LocalDate.of(2025, Month.DECEMBER, 25));
			dailyDez.setEndDate(LocalDate.of(2025, Month.DECEMBER, 25));
			dailyDez.setRate(0.1);
			taxRateRepository.save(dailyDez);

		}
		log.info("Municipality count in database: " + municipalityRepository.count());

	}
}
