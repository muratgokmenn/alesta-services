package com.alesta.customer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alesta.customer.exception.AddressException;
import com.alesta.customer.exception.AdressExceptionConstants;
import com.alesta.customer.model.Address;
import com.alesta.customer.repository.AddressRepository;
import com.alesta.response.ApiResponse;
import com.alesta.utility.ValidationHelper;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository addressRepository; 

	public List<Address> getAddressesByCustomerId(Long customerId){
		return addressRepository.findAllByCustomerId(customerId);
	}

	public ApiResponse<Address> updateAddress(Long addressId, Address addressDetails) throws AddressException {

		validationAddresRequest(addressDetails);
		
		Optional<Address> address = addressRepository.findById(addressId);
		
		checkInAddressExistsInDatabase(address);

		address.get().setCity(addressDetails.getCity());
		address.get().setCountry(addressDetails.getCountry());
		address.get().setDistrict(addressDetails.getDistrict());
		address.get().setFloor(addressDetails.getFloor());
		address.get().setNeighbourhood(addressDetails.getNeighbourhood());
		address.get().setNo(addressDetails.getNo());
		addressRepository.save(address.get());
		return new ApiResponse<>(true, "Address updated is succesfully", addressDetails);
		
	}

	private void checkInAddressExistsInDatabase(Optional<Address> address) throws AddressException {
		if(!address.isPresent()) 
			throw new AddressException("Address did not found");
	}

	private void validationAddresRequest(Address addressDetails) throws AddressException {
		if(!ValidationHelper.isValid(addressDetails.getCustomer().getId()))
			throw new AddressException(AdressExceptionConstants.CUSTOMER_ID_IS_NOT_VALID);
		if(!ValidationHelper.isValid(addressDetails.getCountry()))
			throw new AddressException(AdressExceptionConstants.COUNTRY_IS_NOT_VALID);
		if(!ValidationHelper.isValid(addressDetails.getCity()))
			throw new AddressException(AdressExceptionConstants.CITY_IS_NOT_VALID);
		if(!ValidationHelper.isValid(addressDetails.getDistrict()))
			throw new AddressException(AdressExceptionConstants.DISTRICT_IS_NOT_VALID);
		if(!ValidationHelper.isValid(addressDetails.getNeighbourhood()))
			throw new AddressException(AdressExceptionConstants.NEIGHBOURHOOD_IS_NOT_VALID);
		if(!ValidationHelper.isValid(addressDetails.getNo()))
			throw new AddressException(AdressExceptionConstants.NO_IS_NOT_VALID);
		if(!ValidationHelper.isValid(addressDetails.getFloor()))
			throw new AddressException(AdressExceptionConstants.FLOOR_IS_NOT_VALID);
	}

}
