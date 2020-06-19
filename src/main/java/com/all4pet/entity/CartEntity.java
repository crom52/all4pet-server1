
package com.all4pet.entity;

import java.util.List;



public class CartEntity {

	private UserEntity userEntity;
	private List<CartItemEntity> listCartItem;
	private long id;
	private String cartCode;
	
	public List<CartItemEntity> getListCartItem() {
		return listCartItem;
	}

	public void setListCartItem(List<CartItemEntity> listCartItem) {
		this.listCartItem = listCartItem;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCartCode() {
		return cartCode;
	}

	public void setCartCode(String cartCode) {
		this.cartCode = cartCode;
	}



}
