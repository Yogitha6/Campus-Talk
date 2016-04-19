package com.campusTalk.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="userId", nullable=false)
	private int userId;
	
	@Column(name="firstName", nullable=false)
	private String firstname;
	
	@Column(name="lastName")
	private String lastname;
	
	@Column(name="emailId", nullable=false)
	private String emailId;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@Column(name="major")
	private String major;
	
	public User(){};

	public User(String firstName, String lastName, String emailId, String password, String major){
		this.firstname = firstName;
		this.lastname = lastName;
		this.emailId = emailId;
		this.password = password;
		this.major = major;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
}
