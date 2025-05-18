package com.example.spring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring.entities.Municipality;
import com.example.spring.entities.TaxRate;
import com.example.spring.repository.TaxRateRepository;

@SpringBootTest
@Transactional  // Rolls back DB changes after each test
class TaxRateServiceIntegrationTest {

    @Autowired
    private TaxRateService taxRateService;
    
    @Autowired
    private MunicipalityService municipalityService;

    @Autowired
    private TaxRateRepository taxRateRepository;

    private TaxRate sampleTaxRate;
    
    private Municipality municipality;

    @BeforeEach
    void setUp() {
    	municipality = new Municipality();
    	municipality.setName("Springfield");
    	municipalityService.saveMunicipality(municipality);
    	
        sampleTaxRate = new TaxRate();
        sampleTaxRate.setPeriodicity("Monthly");
        sampleTaxRate.setStartDate(LocalDate.of(2025, Month.JANUARY, 1));
        sampleTaxRate.setEndDate(LocalDate.of(2025, Month.DECEMBER, 31));
        sampleTaxRate.setRate(0.15);
        sampleTaxRate.setMunicipality(municipality);

        taxRateRepository.save(sampleTaxRate);
    }

    @Test
    void shouldCreateRate_whenSaveIsCalledAndEntityDoesNotExist() {
        TaxRate newRate = new TaxRate();
        newRate.setPeriodicity("Weekly");
        newRate.setStartDate(LocalDate.of(2025, Month.JANUARY, 1));
        newRate.setEndDate(LocalDate.of(2025, Month.DECEMBER, 31));
        newRate.setRate(0.2);
        newRate.setMunicipality(municipality);

        TaxRate saved = taxRateService.saveTaxRate(newRate);

        assertNotNull(saved.getId());
        assertEquals("Springfield", saved.getMunicipality().getName());
    }

    @Test
    void shouldRetrieveRate_whenGetRateByIdIsCalled() {
        Optional<TaxRate> found = taxRateService.getTaxRate(sampleTaxRate.getId());
        assertTrue(found.isPresent());
        assertEquals("Springfield", found.get().getMunicipality().getName());
    }

    @Test
    void shouldUpdateRate_whenSaveIsCalledAndEntityExists() {
    	municipality.setName("UpdatedCity");
    	municipalityService.saveMunicipality(municipality);
    	
        TaxRate update = new TaxRate();
        update.setPeriodicity("Yearly");
        update.setStartDate(LocalDate.of(2026, Month.JANUARY, 1));
        update.setEndDate(LocalDate.of(2026, Month.DECEMBER, 31));
        update.setRate(0.30);
        update.setMunicipality(municipality);

        TaxRate result = taxRateService.updateTaxRate(update, sampleTaxRate.getId());

        assertEquals("Yearly", result.getPeriodicity());
        assertEquals("UpdatedCity", result.getMunicipality().getName());
        assertEquals(0.30, result.getRate());
    }

    @Test
    void shouldDeleteRate_whenDeleteIsCalledAndEntityExists() {
        taxRateService.deleteTaxRate(sampleTaxRate.getId());
        Optional<TaxRate> deleted = taxRateService.getTaxRate(sampleTaxRate.getId());
        assertFalse(deleted.isPresent());
    }

    @Test
    void shouldRetrieveAllRates_whenFindAllIsCalled() {
        List<TaxRate> all = taxRateService.findAll();
        assertFalse(all.isEmpty());
    }

    @Test
    void shouldRetrieveRate_whenFindByMuniciplityIsCalledAndRatesExist() {
        List<TaxRate> list = taxRateService.findByMunicipality(municipality);
        assertEquals(1, list.size());
        assertEquals("Springfield", list.get(0).getMunicipality().getName());
    }
    
    @Test
    void shouldReturnEmptyList_whenFindByMuniciplityIsCalledAndNoRateIsFound() {
    	Municipality municipality = new Municipality();
    	municipality.setId(10L);
    			
        List<TaxRate> list = taxRateService.findByMunicipality(municipality);
        assertTrue(list.isEmpty());
    }
}

