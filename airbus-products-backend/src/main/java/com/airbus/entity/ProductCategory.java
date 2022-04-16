package com.airbus.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "PRODUCT_CATEGORY")
@Data
public class ProductCategory {

	@Id
	@SequenceGenerator(name = "categoryIdSequence", sequenceName = "categoryIdSequence", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "categoryIdSequence")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "CATEGORY_NAME")
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy="category")
	private Collection<Product> product = new ArrayList<>();
}
