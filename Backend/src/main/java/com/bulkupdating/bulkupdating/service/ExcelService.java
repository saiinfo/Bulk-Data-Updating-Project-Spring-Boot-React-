 package com.bulkupdating.bulkupdating.service;


 import org.apache.poi.ss.usermodel.*;
 import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.web.multipart.MultipartFile;

import com.bulkupdating.bulkupdating.domain.Customer;
import com.bulkupdating.bulkupdating.repository.CustomerRepository;

import jakarta.transaction.Transactional;

import java.io.IOException;
 import java.io.InputStream;
 import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
 import java.util.List;

@Service
public class ExcelService {

    @Autowired
    private CustomerRepository customerRepository; // Assuming you have a CustomerRepository

    @Transactional
    public List<Customer> processExcel(MultipartFile file) {
        List<Customer> customers = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

         // Skip the first row (header row)
         Iterator<Row> rowIterator = sheet.iterator();
         if (rowIterator.hasNext()) {
             rowIterator.next(); // Skip the header row
         }

         while (rowIterator.hasNext()) {
             Row row = rowIterator.next();
             Customer customer = new Customer();
             
//             Cell firstNameCell = row.getCell(0);
//             if (firstNameCell.getCellType() == CellType.STRING) {
//                 customer.setFirstName(firstNameCell.getStringCellValue());
//             }
//             
//             Cell lastNameCell = row.getCell(1);
//             if (lastNameCell.getCellType() == CellType.STRING) {
//                 customer.setLastName(lastNameCell.getStringCellValue());
//             }
//             
//             Cell phoneCell = row.getCell(2);
//             if (phoneCell.getCellType() == CellType.NUMERIC) {
//                 customer.setPhone((long) phoneCell.getNumericCellValue());
//             }
             
             Cell nameCell = row.getCell(0);
             if (nameCell.getCellType() == CellType.STRING) {
                 String[] nameParts = nameCell.getStringCellValue().split("\\s+", 3);
                 System.out.println("size"+nameParts.length);
                 if(nameParts.length>2) {
                	 
                 
                 if (nameParts.length > 0) {
                     customer.setFirstName(nameParts[0]);
                     if (nameParts.length > 1) {
                         customer.setMiddleName(nameParts[1]);
                         if (nameParts.length > 2) {
                             customer.setLastName(nameParts[2]);
                         }
                     }
                 }
                 }else
                 {
                	 if (nameParts.length > 0) {
                         customer.setFirstName(nameParts[0]);
                         if (nameParts.length > 1) {
                        	 customer.setLastName(nameParts[1]);
                         }
                     }
                	 
                 }
             }
             
//             Cell nameCell = row.getCell(0);
//             if (nameCell.getCellType() == CellType.STRING) {
//                 String[] nameParts = nameCell.getStringCellValue().split("\\s+", 2);
//                 if (nameParts.length > 0) {
//                     customer.setFirstName(nameParts[0]);
//                     if (nameParts.length > 1) {
//                         customer.setLastName(nameParts[1]);
//                     }
//                 }
//             }

             Cell phoneCell = row.getCell(1);
             if (phoneCell.getCellType() == CellType.NUMERIC) {
                 customer.setPhone((long) phoneCell.getNumericCellValue());
             }
             
//             Cell cityCell = row.getCell(2);
//             if (cityCell.getCellType() == CellType.STRING) {
//                 String city = cityCell.getStringCellValue();
//                 customer.setCity(city);
//             }
             
             customer.setAcquiredDate(new Date());
             customers.add(customer);
             customerRepository.save(customer); // Save customer to the database
         }

           

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }

        return customers;
    }
    
    public Workbook exportToExcel(List<Customer> customers) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Customer Data");

        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("First Name");
//        headerRow.createCell(1).setCellValue("Middle Name");
//        headerRow.createCell(2).setCellValue("Last Name");
        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("Phone");
        headerRow.createCell(2).setCellValue("Acquired Date");
       // headerRow.createCell(3).setCellValue("City");
        
        
//        sheet.setColumnWidth(0, 4000); // Adjust the width as needed
//        sheet.setColumnWidth(1, 4000);
//        sheet.setColumnWidth(2, 4000);
//        sheet.setColumnWidth(3, 4000);
//        sheet.setColumnWidth(4, 6000);
        

        sheet.setColumnWidth(0, 8000); 
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 4000);
         
        
        int rowNum = 1;
        for (Customer customer : customers) {
            Row dataRow = sheet.createRow(rowNum++);
            if(customer.getFirstName()!=null && customer.getMiddleName()!=null && customer.getLastName()!=null) {
            dataRow.createCell(0).setCellValue(customer.getFirstName()+"  "+customer.getMiddleName()+"  "+customer.getLastName());
            }else
            {
            	dataRow.createCell(0).setCellValue(customer.getFirstName()+"  "+customer.getLastName());
            }
//            dataRow.createCell(1).setCellValue(customer.getMiddleName());
//            dataRow.createCell(2).setCellValue(customer.getLastName());
            dataRow.createCell(1).setCellValue(customer.getPhone());
            dataRow.createCell(2).setCellValue(customer.getAcquiredDate().toString());
          //  dataRow.createCell(3).setCellValue(customer.getCity());
        }

        return workbook;
    } 
    
    
}
