package com.alesta.user.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alesta.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
    boolean existsByUserCodeOrEmailOrTelephoneNo(String userCode, String email, String telephoneNo);
    
    Optional<User> findByUserCodeOrEmail(String userCode, String email);

	
}
