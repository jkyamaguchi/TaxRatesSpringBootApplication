package com.example.spring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring.entities.Municipality;
import com.example.spring.entities.TaxRate;
import com.example.spring.repository.MunicipalityRepository;
import com.example.spring.repository.TaxRateRepository;

@SpringBootTest
@Transactional
public class TaxRateRetrieveServiceTest {

    @Autowired
    private TaxRateRetrieveService taxRateRetrieveService;

    @Autowired
    private TaxRateRepository taxRateRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;
    
    private Municipality municipality;

    @BeforeEach
    void setUp() {
        municipality = new Municipality();
        municipality.setName("Springfield");
        municipalityRepository.save(municipality);

        TaxRate daily1 = new TaxRate();
        daily1.setRate(0.1);
        daily1.setPriority(1);
        daily1.setStartDate(LocalDate.of(2025, Month.JANUARY, 1));
        daily1.setEndDate(LocalDate.of(2025, Month.JANUARY, 1));
        daily1.setPeriodicity("Daily");
        daily1.setMunicipality(municipality);
        
        TaxRate daily2 = new TaxRate();
        daily2.setRate(0.1);
        daily2.setPriority(1);
        daily2.setStartDate(LocalDate.of(2025, Month.DECEMBER, 25));
        daily2.setEndDate(LocalDate.of(2025, Month.DECEMBER, 25));
        daily2.setPeriodicity("Daily");
        daily2.setMunicipality(municipality);
        
        TaxRate monthly = new TaxRate();
        monthly.setRate(0.4);
        monthly.setPriority(2);
        monthly.setStartDate(LocalDate.of(2025, Month.MAY, 1));
        monthly.setEndDate(LocalDate.of(2025, Month.MAY, 31));
        monthly.setPeriodicity("Yearly");
        monthly.setMunicipality(municipality);

        TaxRate yearly = new TaxRate();
        yearly.setRate(0.2);
        yearly.setPriority(3);
        yearly.setStartDate(LocalDate.of(2025, Month.JANUARY, 1));
        yearly.setEndDate(LocalDate.of(2025, Month.DECEMBER, 31));
        yearly.setPeriodicity("Yearly");
        yearly.setMunicipality(municipality);

        taxRateRepository.saveAll(Arrays.asList(daily1, daily2, monthly, yearly));
    }

    @Test
    void shouldRetrieveDailyTax_whenSelectedDateIsInDailyTax() {
        LocalDate date = LocalDate.of(2025, Month.JANUARY, 1);
        Double rate = taxRateRetrieveService.retrieveTaxRate(municipality, date);
        assertEquals(0.1, rate);
    }

    @Test
    void shouldRetrieveMonthlyTax_whenSelectedDateIsInMontlyTax() {
        LocalDate date = LocalDate.of(2025, Month.MAY, 10);
        Double rate = taxRateRetrieveService.retrieveTaxRate(municipality, date);
        assertEquals(0.4, rate);
    }
    
    @Test
    void shouldRetrieveYearlyTax_whenSelectedDateIsInYearlyTax() {
        LocalDate date = LocalDate.of(2025, Month.DECEMBER, 20);
        Double rate = taxRateRetrieveService.retrieveTaxRate(municipality, date);
        assertEquals(0.2, rate);
    }

}
