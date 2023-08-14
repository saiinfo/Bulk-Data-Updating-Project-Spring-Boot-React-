package com.bulkupdating.bulkupdating.domain;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;



@Entity
public class Customer {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long customerId;
private String firstName;
private String middleName;
private String lastName;
private long phone;

private Date acquiredDate;
public Long getCustomerId() {
	return customerId;
}
public void setCustomerId(Long customerId) {
	this.customerId = customerId;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}


public String getMiddleName() {
	return middleName;
}
public void setMiddleName(String middleName) {
	this.middleName = middleName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public long getPhone() {
	return phone;
}
public void setPhone(long phone) {
	this.phone = phone;
}
public Date getAcquiredDate() {
	return acquiredDate;
}
public void setAcquiredDate(Date acquiredDate) {
	this.acquiredDate = acquiredDate;
}

//@JsonIgnore
//@ManyToOne(fetch = FetchType.LAZY)  // Many customers can belong to one city
//@JoinColumn(name = "city_id")      // Name of the foreign key column in the customer table
//private City city;


@ManyToOne
@JoinColumn(name = "city_id")
private City city;

public Customer() {
	super();
	// TODO Auto-generated constructor stub
}

public City getCity() {
	return city;
}
public void setCity(City city) {
	this.city = city;
	
}
@Override
public String toString() {
	return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", middleName=" + middleName
			+ ", lastName=" + lastName + ", phone=" + phone + ", acquiredDate=" + acquiredDate + ", city=" + city + "]";
}
public Customer(Long customerId, String firstName, String middleName, String lastName, long phone, Date acquiredDate,
		City city) {
	super();
	this.customerId = customerId;
	this.firstName = firstName;
	this.middleName = middleName;
	this.lastName = lastName;
	this.phone = phone;
	this.acquiredDate = acquiredDate;
	this.city = city;
}





	
}
