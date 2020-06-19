
package com.all4pet.entity;

import org.springframework.beans.factory.annotation.Autowired;

public class UserEntity {
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Autowired RoleUserEntity roleUserEntity;
	private long id;
	private String userName;
	private String password;
	private String email;
	private int active;
	private RoleUserEntity role;
	public UserEntity( String userName, String password, String email, int active,RoleUserEntity role) {
			this.userName = userName;
			this.password = password;
			this.email = email;
			this.active = active;
			this.role = role;
	}


	  public UserEntity() {
	    }

	public Long getId() {
		return id;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public RoleUserEntity getRole() {
		return role;
	}


	public void setRole(RoleUserEntity role) {
		this.role = role;
	}


//
//	public List<BillEntity> getListBills() {
//		return listBills;
//	}

//	public void setListBills(List<BillEntity> listBills) {
//		this.listBills = listBills;
//	}
//
//	public CartEntity getCartEntity() {
//		return cartEntity;
//	}
//
//	public void setCartEntity(CartEntity cartEntity) {
//		this.cartEntity = cartEntity;
//	}
//	
}
