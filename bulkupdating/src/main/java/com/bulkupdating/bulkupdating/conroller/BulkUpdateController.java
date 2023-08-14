package com.bulkupdating.bulkupdating.conroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bulkupdating.bulkupdating.domain.City;
import com.bulkupdating.bulkupdating.domain.Customer;
import com.bulkupdating.bulkupdating.repository.CityRepository;
import com.bulkupdating.bulkupdating.repository.CustomerRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin 
@RestController
@RequestMapping("/api")
public class BulkUpdateController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private final CityRepository cityRepository;

    public BulkUpdateController(CustomerRepository customerRepository, CityRepository cityRepository) {
        this.customerRepository = customerRepository;
        this.cityRepository = cityRepository;
    }
   
    
    
    @PutMapping("/bulk-update/{cityId}")
    public ResponseEntity<String> bulkUpdate(
            @PathVariable Long cityId,
            @RequestBody List<Map<String, Long>> customerIdsList) {

        Optional<City> optionalCity = cityRepository.findById(cityId);
        
        if (optionalCity.isEmpty()) {
            return ResponseEntity.badRequest().body("City not found");
        }
        
        City city = optionalCity.get();
        
        List<Long> customerIds = customerIdsList.stream()
                .map(map -> map.get("customerId"))
                .collect(Collectors.toList());

        List<Customer> customersToUpdate = customerRepository.findAllById(customerIds);

        for (Customer customer : customersToUpdate) {
            customer.setCity(city);
        }

        customerRepository.saveAll(customersToUpdate);

        return ResponseEntity.ok("Bulk update successful");
    }

    
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomer() {
        List<Customer> customers =customerRepository.findAll();
        return ResponseEntity.ok(customers);
    }


}