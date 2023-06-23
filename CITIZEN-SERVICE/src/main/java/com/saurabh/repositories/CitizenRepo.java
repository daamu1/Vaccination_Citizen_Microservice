package com.saurabh.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saurabh.Entity.Citizen;

public interface CitizenRepo extends JpaRepository<Citizen, Integer>{
	
	public List<Citizen> findByVaccinationCenterId(Integer id);

}
