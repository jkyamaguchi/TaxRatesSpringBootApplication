package com.example.spring.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TaxRate{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String periodicity; //yearly, monthly, daily
	
	private Integer priority;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // Required for HTML5 date format
	private LocalDate startDate;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // Required for HTML5 date format
	private LocalDate endDate;
	
	private Double rate;

	@ManyToOne(targetEntity = Municipality.class)
	private Municipality municipality;

}
