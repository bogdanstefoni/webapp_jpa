package com.bogdan.webapp.entity;

import org.apache.commons.lang3.StringUtils;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "students")
public class Student extends BaseEntity{



	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "username")
	@NotBlank
	private String username;

	@Column(name = "password")
	@NotBlank
	private String password;

	@Transient
	private String fullName;

	public Student() {
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return getFirstName()  + StringUtils.SPACE + getLastName();
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}



	@Override
	public String toString() {
		return "Student{" + " firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
				+ ", email='" + email + '\'' + '}';
	}
}
