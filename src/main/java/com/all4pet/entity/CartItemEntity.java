

package com.all4pet.entity;


public class CartItemEntity{

	private ProductEntity productEntity;
	private int quantity;
	private long cartId;
	private long id;
	private String cartCode;



	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}



	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public String getCartCode() {
		return cartCode;
	}

	public void setCartCode(String cartCode) {
		this.cartCode = cartCode;
	}

	
	
	
	
}
