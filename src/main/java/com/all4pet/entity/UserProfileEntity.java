

package com.all4pet.entity;

import java.util.Date;



public class UserProfileEntity {
	
	private String name;
	private String address;
	private Date birthday;
	private String phoneNumber;
	private Date createdDate;
	private UserEntity userEntity;
	
	public UserProfileEntity( Date createdDate, UserEntity userEntity) {
		this.createdDate = createdDate;
		this.userEntity = userEntity;
	}
	public UserProfileEntity( String name, String address, Date birthday, Date createdDate, String phoneNumber, UserEntity user) {
		this.name = name;
		this.address = address;
		this.birthday = birthday;
		this.createdDate = createdDate;
		this.phoneNumber = phoneNumber;
		this.userEntity = user;
}
	public UserProfileEntity() {		
}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
