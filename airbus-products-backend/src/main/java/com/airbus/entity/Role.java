package com.airbus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ROLES")
@Data
public class Role {

	@Id
	@SequenceGenerator(name = "roleIdSequence", sequenceName = "roleIdSequence", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "roleIdSequence")
	@Column(name = "ID")
	private Long id;

	@Column(name = "ROLE_NAME")
	private String name;
}
