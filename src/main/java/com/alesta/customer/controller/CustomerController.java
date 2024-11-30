package com.alesta.customer.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alesta.customer.exception.CustomerException;
import com.alesta.customer.model.Customer;
import com.alesta.customer.service.CustomerService;
import com.alesta.response.ApiResponse;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
		
    @PostMapping("/register")
    public ApiResponse<Customer> saveCustomer(@RequestBody Customer customer) {
    	try {
			return customerService.saveCustomerWithAdress(customer);
		} catch (CustomerException e) {
			return new ApiResponse<>(false, "Customer registered failed : " + e.getMessage(), customer);
		}
    }
    
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Customer>> getCustomerById(@PathVariable Long id){
    	return ResponseEntity.ok(customerService.getCustomerById(id));
    }
    
    @PutMapping("update/{id}")
    public ApiResponse<Customer> updateCustomer(@PathVariable(value="id") Long customerId, @RequestBody Customer customer) {
		try {
			return customerService.updateCustomer(customerId, customer);
		} catch (CustomerException e) {
			return  new ApiResponse<>(false, "Customer updating failed : " + e.getMessage(), customer);
		}
    }
    
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable Long id) {
    	customerService.deleteCustomerById(id);
    	return ResponseEntity.ok("Delete Success");
    }

}
