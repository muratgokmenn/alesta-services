package com.alesta.order.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.alesta.customer.model.Customer;
import com.alesta.product.model.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@jakarta.persistence.Entity
@Table(name = "`order`") 
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Order {
	
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
    
   /* @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
   
    @ManyToOne
    @JoinColumn(name = "product_id") 
    private Product product;*/
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;
   
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
    
    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate; //TODO hedeflenen teslim tarihi anlamına gelmeli
    
    @Column(name = "deposit_price", nullable = false)
    private BigDecimal depositPrice;
    
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    
    @Column(name = "remaining_price")
    private BigDecimal remainingPrice;

}
