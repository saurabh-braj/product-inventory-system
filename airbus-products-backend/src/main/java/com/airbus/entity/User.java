package com.airbus.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "USERS")
@Data
public class User {

	@Id
	@SequenceGenerator(name = "userIdSequence", sequenceName = "userIdSequence", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "userIdSequence")
	@Column(name = "ID")
	private Long id;

	@Column(name = "USER_ID")
	private String userName;

	@Column(name = "PASSWORD")
	private String password;

	@OneToMany(fetch = FetchType.EAGER)
	private List<Role> roles = new ArrayList<>();
}
