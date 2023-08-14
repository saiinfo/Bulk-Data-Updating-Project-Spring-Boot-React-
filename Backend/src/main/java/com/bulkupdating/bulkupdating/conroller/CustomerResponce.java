package com.bulkupdating.bulkupdating.conroller;




import java.util.List;

import com.bulkupdating.bulkupdating.domain.Customer;



public class CustomerResponce {

    private List<Customer> customers;
    private String message;
    private int totalPages;
    private long totalElements;

    public CustomerResponce(List<Customer> customers, int totalPages, long totalElements) {
        this.customers = customers;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }


    public CustomerResponce() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "CustomerResponce [customers=" + customers + ", message=" + message + ", totalPages=" + totalPages
				+ ", totalElements=" + totalElements + "]";
	}


	public CustomerResponce(List<Customer> customers, String message, int totalPages, long totalElements) {
		super();
		this.customers = customers;
		this.message = message;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
	}


	public List<Customer> getCustomers() {
		return customers;
	}


	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}


	public int getTotalPages() {
		return totalPages;
	}


	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}


	public long getTotalElements() {
		return totalElements;
	}


	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}


	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

