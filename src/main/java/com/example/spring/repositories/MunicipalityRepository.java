package com.example.spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring.entities.Municipality;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long>{
	
	//List by name
	List<Municipality> findByName(String name);
	
	Optional<Municipality> findById(Long id);
	
}
