package com.alesta.user.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.alesta.response.ApiResponse;
import com.alesta.user.constants.UserConstants;
import com.alesta.user.exception.UserException;
import com.alesta.user.exception.UserExceptionConstants;
import com.alesta.user.model.User;
import com.alesta.user.repository.UserRepository;
import com.alesta.utility.ValidationHelper;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ApiResponse<User> saveUser(User user) throws UserException {
    	validateUserSavingRequest(user);
    	checkUserExistsInDatabase(user);
    	
    	String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
    	userRepository.save(user);
    	
        return new ApiResponse<>(true, UserConstants.USER_REGISTERED_SUCCESSFUL_MESSAGE, user);
    }
    
	private void checkUserExistsInDatabase(User user) throws UserException {
		if(userRepository.existsByUserCodeOrEmailOrTelephoneNo(user.getUserCode(), user.getEmail() , user.getTelephoneNo())) {
			 throw new ResponseStatusException(HttpStatus.CONFLICT, UserExceptionConstants.USER_INFORMATION_EXISTS);
    	}
	}

	private void validateUserSavingRequest(User user) throws UserException {
		if (!ValidationHelper.isValid(user.getUserCode())) 
			throw new UserException(UserExceptionConstants.USER_CODE_IS_REQUIRED);
		if (user.getPassword() == null || user.getPassword().length() < 8) 
			throw new UserException(UserExceptionConstants.INVALID_PASSWORD);
		if (!ValidationHelper.isValidEmail(user.getEmail())) 
			throw new UserException(UserExceptionConstants.INVALID_EMAIL);
		if (!ValidationHelper.isValidTelephoneNo(user.getTelephoneNo())) 
			throw new UserException(UserExceptionConstants.INVALID_TELEPHONE_NUMBER);
		if (!ValidationHelper.isValid(user.getCompanyCode())) 
			throw new UserException(UserExceptionConstants.COMPANY_NAME_IS_REQUIRED);
	}
	
	private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
	
	public ApiResponse<User> loginUser(User user) throws UserException {
		validateUserLoginRequest(user);
	
		User userDataFromDatabase = userRepository.findByUserCodeOrEmail(user.getUserCode(), user.getEmail()).
				orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UserExceptionConstants.USER_INFORMATION_NOT_EXISTS));
	
		String hashedPassword = userDataFromDatabase.getPassword();
		validUserLogin(user.getPassword(), hashedPassword);
		
		return new ApiResponse<>(true, "User login successfully", user);
	}
 
	private void validateUserLoginRequest(User user) throws UserException {
		if(!ValidationHelper.isValid(user.getUserCode()) && !ValidationHelper.isValid(user.getEmail()))
			throw new UserException(UserExceptionConstants.USER_CODE_OR_EMAIL_IS_REQUIRED);
		if(!ValidationHelper.isValid(user.getPassword()))
			throw new UserException(UserExceptionConstants.INVALID_PASSWORD);
	}
	
	private void validUserLogin(String password, String hashedPassword) throws UserException {
		if(!verifyPassword(password, hashedPassword))
			throw new UserException(UserExceptionConstants.PASSWORD_IS_WRONG);
	}

	/*
	 * Kullanıcı giriş yaptığında,
	 *  girilen şifreyi veritabanında saklanan hash ile karşılaştırmak 
	 *  için BCrypt.checkpw metodunu kullanabilirsin. Bu, düz metin şifre ile hashin eşleşip eşleşmediğini kontrol eder:
	 */
	public boolean verifyPassword(String plainPassword, String hashedPassword) {
	    return BCrypt.checkpw(plainPassword, hashedPassword);
	}
}
