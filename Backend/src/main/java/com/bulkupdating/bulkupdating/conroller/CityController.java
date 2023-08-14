package com.bulkupdating.bulkupdating.conroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bulkupdating.bulkupdating.domain.City;
import com.bulkupdating.bulkupdating.repository.CityRepository;

@CrossOrigin 
@RestController
@RequestMapping("/api")
public class CityController {

	@Autowired
    private final CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getCities() {
        List<City> cities = cityRepository.findAll();
        return ResponseEntity.ok(cities);
    }
}