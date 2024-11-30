package com.alesta.user.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@jakarta.persistence.Entity
public class User{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "status", nullable = false)
    private int status = 1;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    private String userCode;
    
    private String password;
    
    private String email; //nullable = false kuralını ekle
    
    @Column(name = "telephoneNo", nullable = false)
    private String telephoneNo;
    
    private String companyCode; //TODO parametre tablosunu oluştur

}

