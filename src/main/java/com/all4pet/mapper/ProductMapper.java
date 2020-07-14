package com.all4pet.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import com.all4pet.entity.CategoryEntity;
import com.all4pet.entity.ProductEntity;

@Mapper
public interface ProductMapper {

    public List<ProductEntity> getAllProduct();
    public List<CategoryEntity> getAllCategoryName();
    public List<ProductEntity> getProductByCategoryName(String categoryName);
	public List<ProductEntity> paging10Products(int offset);
	public List<ProductEntity> get10ProductByCategoryAndType(int offset, String category, String type);
	public ProductEntity getProductById(long id );
	public ProductEntity getProductByName(String name);
	public void deleteProductById(long id);
	public void insertProduct(ProductEntity product);
	public CategoryEntity getCategoryByName(String categoryName);
	public void updateProductById( long id, ProductEntity product);
	
	public List<ProductEntity> getTopProduct();
	public void updateNumOfPurchase(long id, int numOfPurchase);
	public List<ProductEntity> getProductBySearchKey(String key);
	public List<ProductEntity> getRelateProduct(String type);
	public List<ProductEntity> getAllProduct2();

	

	

}