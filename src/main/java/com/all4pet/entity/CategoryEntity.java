
package com.all4pet.entity;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class CategoryEntity{

//    private List<ProductEntity> listProducts = new ArrayList<>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long categoryid;
	private List<ProductEntity> listProducts;
	private String categoryName;
	public List<ProductEntity> getListProducts() {
		return listProducts;
	}

	public void setListProducts(List<ProductEntity> listProducts) {
		this.listProducts = listProducts;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(long categoryid) {
		this.categoryid = categoryid;
	}
	
	
}
