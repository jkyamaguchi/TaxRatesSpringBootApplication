package com.example.spring.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spring.entities.Municipality;
import com.example.spring.entities.TaxRate;

@Repository
public interface TaxRateRepository extends JpaRepository<TaxRate, Long> {

	Optional<TaxRate> findById(Long id);
	
	List<TaxRate> findByMunicipality(Municipality municipality);
	
	@Query("SELECT t FROM TaxRate t WHERE t.municipality = :municipality " +
		       "AND t.startDate <= :date AND t.endDate >= :date " +
		       "ORDER BY t.priority ASC")
	List<TaxRate> findByMunicipalityAndDate(
		@Param("municipality") Municipality municipality,
		@Param("date") LocalDate date
	);
}
