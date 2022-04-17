package com.airbus.domain;

import org.springframework.data.domain.Sort.Direction;

import com.airbus.entity.Product;

import lombok.Data;

@Data
public class PageRequestModel {
	
	private int pageNumber;
	private int pageSize;
	private String sortColumn;
	private Direction sortDirection;
	private Product product;
}
