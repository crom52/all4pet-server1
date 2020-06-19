/*
	@author:Quang Truong
	@date: Jan 15, 2020
*/

package com.all4pet.entity;


import java.util.List;



public class RoleUserEntity{
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long roleid;
	private String rolename;
	private List<UserEntity> listUsers;
	
	
	public String getRoleName() {
		return rolename;
	}

	public void setRoleName(String rolename) {
		this.rolename = rolename;
	}


	public long getRoleid() {
		return roleid;
	}

	public void setRoleid(long roleid) {
		this.roleid = roleid;
	}
	

	public List<UserEntity> getListUsers() {
		return listUsers;
	}

	public void setListUsers(List<UserEntity> listUsers) {
		this.listUsers = listUsers;
	}



	
	
}
