
package com.all4pet.entity;

public class ProductEntity {
	private long id;
	private String name;
	private int amount;
	private String url;
	private float price;
	private int promotion;
	private String type;
	private String url2;
	private String url3;
	private String description;
	private String brand;
	private int numOfPurchase;
	private String size;
	private String color;
	private CategoryEntity category;

public ProductEntity(  String name, int amount, String url,
		 float price, int promotion, String type, String url2, 
		 String url3, String brand, String description,
		 int numOfPurchase, CategoryEntity category,String size, String color) {
			 
		
		this.name = name;
		this.amount = amount;
		this.url = url;
		this.price = price;
		this.promotion = promotion;
		this.type = type;
		this.url2 = url2;
		this.url3 = url3;
		this.brand = brand;
		this.description = description;
		this.category = category;
		this.numOfPurchase = numOfPurchase;
		this.size= size;
		this.color = color;
		
				
	}
public ProductEntity () {
	
}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl2() {
		return url2;
	}

	public void setUrl2(String url2) {
		this.url2 = url2;
	}

	public String getUrl3() {
		return url3;
	}

	public void setUrl3(String url3) {
		this.url3 = url3;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getPromotion() {
		return promotion;
	}

	public void setPromotion(int promotion) {
		this.promotion = promotion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	public CategoryEntity getCategory() {
		return category;
	}



	public void setCategory(CategoryEntity category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getNumOfPurchase() {
		return numOfPurchase;
	}
	public void setNumOfPurchase(int numOfPurchase) {
		this.numOfPurchase = numOfPurchase;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}


}
