package com.bulkupdating.bulkupdating.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bulkupdating.bulkupdating.domain.City;

@Repository
public interface CityRepository  extends JpaRepository<City, Long>{

	  Optional<City> findById( long cityId);
}
