package com.alesta.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alesta.customer.model.Address;
import com.alesta.customer.service.AddressService;
import com.alesta.response.ApiResponse;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@PutMapping("update/{addressId}")
	public ApiResponse<Address> updateAddress(@PathVariable Long addressId, @RequestBody Address addressDetails) {
		try {
			return addressService.updateAddress(addressId, addressDetails);
		} catch (Exception e) {
			return new ApiResponse<>(false, "Address updating failed : " + e.getMessage(), addressDetails);
		}
	}
	
	@GetMapping("/{customerId}")
	public List<Address> getAddressesByCustomerId(@PathVariable Long customerId){
		return addressService.getAddressesByCustomerId(customerId);
	}
	

}
