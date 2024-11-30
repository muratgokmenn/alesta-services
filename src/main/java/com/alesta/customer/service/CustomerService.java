package com.alesta.customer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alesta.customer.exception.CustomerException;
import com.alesta.customer.exception.CustomerExceptionConstants;
import com.alesta.customer.model.Customer;
import com.alesta.customer.repository.CustomerRepository;
import com.alesta.response.ApiResponse;
import com.alesta.utility.ValidationHelper;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public ApiResponse<Customer> saveCustomerWithAdress(Customer customer) throws CustomerException {
		validateCustomerRequest(customer);
		checkCustomerExistsInDatabase(customer);
	
		customerRepository.save(customer);
		
		// Her adresi müşteri ile ilişkilendir
        customer.getAddress().forEach(address -> address.setCustomer(customer));
	
		return new ApiResponse<>(true, "Customer registry is succesfully", customer);
	}

	private void checkCustomerExistsInDatabase(Customer customer) throws CustomerException {
		if(customerRepository.findByEmailOrPhoneNumber(customer.getEmail(), customer.getPhoneNumber()).isPresent() ) 
			throw new CustomerException(CustomerExceptionConstants.CUSTOMER_EMAIL_OR_PHONE_NUMBER_REGISTERED_BEFORE);
	}

	private void validateCustomerRequest(Customer customer) throws CustomerException {
		if (!ValidationHelper.isValid(customer.getFirstName())) 
			throw new CustomerException(CustomerExceptionConstants.CUSTOMER_FIRST_NAME_IS_REQUIRED);
		if (!ValidationHelper.isValid(customer.getLastName())) 
			throw new CustomerException(CustomerExceptionConstants.CUSTOMER_LAST_NAME_IS_REQUIRED);
		if (!ValidationHelper.isValidEmail(customer.getEmail())) 
			throw new CustomerException(CustomerExceptionConstants.CUSTOMER_EMAIL_IS_NOT_VALID);
		if (!ValidationHelper.isValidTelephoneNo(customer.getPhoneNumber())) 
			throw new CustomerException(CustomerExceptionConstants.CUSTOMER_TELEPHONE_NO_IS_NOT_VALID);
		if(!ValidationHelper.isValid(customer.getCompanyName()))
			throw new CustomerException(CustomerExceptionConstants.COMPANY_NAME_IS_REQUIRED);
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
	
	public Optional<Customer> getCustomerById(Long id){
		return customerRepository.findById(id);
	}

	/*
	 * Sadece müşteri bilgileri güncellenir.
	 * Adress bilgileri güncellenmez.
	 */
	public ApiResponse<Customer> updateCustomer(Long id, Customer customerDetails) throws CustomerException {
		validateCustomerRequest(customerDetails);
		
		return customerRepository.findById(id)
                .map(customer -> {
                    customer.setFirstName(customerDetails.getFirstName());
                    customer.setLastName(customerDetails.getLastName());
                    customer.setEmail(customerDetails.getEmail());
                    customer.setCompanyName(customerDetails.getCompanyName());
                    customer.setPhoneNumber(customerDetails.getPhoneNumber());
                    return new ApiResponse<>(true, "Customer updated is succesfully", customerDetails);
                })
                .orElseThrow(() -> new RuntimeException("Customer not found"));
	}

	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(id);
	}

}
