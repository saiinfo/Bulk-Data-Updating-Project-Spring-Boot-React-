package com.bulkupdating.bulkupdating.conroller;


import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bulkupdating.bulkupdating.domain.Customer;
import com.bulkupdating.bulkupdating.repository.CustomerRepository;
import com.bulkupdating.bulkupdating.service.ExcelService;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@RestController
public class UploadeController {

    @Autowired
    private ExcelService excelService;
    
    @Autowired
    private CustomerRepository customerRepository; // Inject the repository

    @GetMapping("/customers")
    public CustomerResponce getCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageRequest pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        List<Customer> customers = customerPage.getContent();
        int totalPages = customerPage.getTotalPages();
        long totalElements = customerPage.getTotalElements();

        CustomerResponce response = new CustomerResponce(customers, totalPages, totalElements);
        response.setMessage("Customer data retrieved successfully."); // Set your message here

        return response;
    }


    
    

    @GetMapping("/home")
    public String uploadForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        List<Customer> customers = excelService.processExcel(file);
        model.addAttribute("customers", customers);

        return "Uploding Sucessfully";
    }
    
    
    
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<Customer> customers = customerRepository.findAll(); // Retrieve customer data from the repository

        Workbook workbook = excelService.exportToExcel(customers);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=customer_data.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}