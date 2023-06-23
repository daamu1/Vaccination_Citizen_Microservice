 package com.saurabh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saurabh.Entity.Citizen;
import com.saurabh.repositories.CitizenRepo;


@RestController
@RequestMapping("/citizen")
public class CitizenController {

    @Autowired
    private CitizenRepo repo;

    @GetMapping(path ="/id/{id}")
    public ResponseEntity<java.util.List<Citizen>> getById(@PathVariable Integer id) {

        List<Citizen> listCitizen = repo.findByVaccinationCenterId(id);
        return new ResponseEntity<>(listCitizen, HttpStatus.OK);
    }

    @PostMapping(path ="/add")
    public ResponseEntity<Citizen> addCitizen(@RequestBody Citizen newCitizen) {

        Citizen citizen = repo.save(newCitizen);
        return new ResponseEntity<>(citizen, HttpStatus.OK);
    }



}