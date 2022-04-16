package com.airbus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "PRODUCTS")
@Data
public class Product {

	@Id
	@SequenceGenerator(name = "productIdSequence", sequenceName = "productIdSequence", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "productIdSequence")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "PRODUCT_ID")
	private String productId;
	
	@ManyToOne()  
	private ProductCategory category; 
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
    
	@Column(name = "UNITS")
	private Integer units;
}
