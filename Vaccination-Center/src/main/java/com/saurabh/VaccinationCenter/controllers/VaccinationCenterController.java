package com.saurabh.VaccinationCenter.controllers;

import java.util.List;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.saurabh.VaccinationCenter.Entity.VaccinationCenter;
import com.saurabh.VaccinationCenter.Model.Citizen;
import com.saurabh.VaccinationCenter.Model.RequiredResponse;
import com.saurabh.VaccinationCenter.Repos.CenterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;




@RestController
@RequestMapping("/vaccinationcenter")
public class VaccinationCenterController {
	
	@Autowired
	private CenterRepo centerRepo;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping(path ="/add")
	public ResponseEntity<VaccinationCenter> addCitizen(@RequestBody VaccinationCenter vaccinationCenter) {
		
		VaccinationCenter vaccinationCenterAdded = centerRepo.save(vaccinationCenter);
		return new ResponseEntity<>(vaccinationCenterAdded, HttpStatus.OK);
	}
	
	@GetMapping(path = "/id/{id}")
	@HystrixCommand(fallbackMethod = "handleCitizenDownTime")
	public ResponseEntity<RequiredResponse> getAllDadaBasedonCenterId(@PathVariable Integer id){
		RequiredResponse requiredResponse =  new RequiredResponse();
		//1st get vaccination center detail
		VaccinationCenter center  = centerRepo.findById(id).get();
		requiredResponse.setCenter(center);
		
		// then get all citizen registerd to vaccination center
		
		List<Citizen> listOfCitizens = restTemplate.getForObject("http://CITIZEN-SERVICE/citizen/id/"+id, List.class);
		requiredResponse.setCitizens(listOfCitizens);
		return new ResponseEntity<RequiredResponse>(requiredResponse, HttpStatus.OK);
	}

	public ResponseEntity<RequiredResponse> handleCitizenDownTime(@PathVariable Integer id) {
		RequiredResponse requiredResponse =  new RequiredResponse();
		//1st get vaccination center detail
		VaccinationCenter center  = centerRepo.findById(id).get();
		requiredResponse.setCenter(center);
		return   new ResponseEntity<RequiredResponse>(requiredResponse, HttpStatus.OK);
	}



	}