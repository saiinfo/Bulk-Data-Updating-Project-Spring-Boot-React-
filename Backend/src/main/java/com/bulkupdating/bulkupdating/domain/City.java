package com.bulkupdating.bulkupdating.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    private String cityName;

//    @JsonIgnore
//    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)  // One city can have many customers
//    private List<Customer> customers = new ArrayList<>();     // List of associated customers

    @JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "city", fetch = FetchType.LAZY)
	private List<Customer> customers;
    
	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", cityName=" + cityName + ", customers=" + customers + "]";
	}

	public City(Long cityId, String cityName, List<Customer> customers) {
		super();
		this.cityId = cityId;
		this.cityName = cityName;
		this.customers = customers;
	}

	public City() {
		super();
		// TODO Auto-generated constructor stub
	}

    

}
