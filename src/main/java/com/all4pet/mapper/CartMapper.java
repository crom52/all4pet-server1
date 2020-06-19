package com.all4pet.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.all4pet.entity.CartEntity;
import com.all4pet.entity.CartItemEntity;
import com.all4pet.entity.ProductEntity;

@Mapper
public interface CartMapper {
	public void updateOrCreateCartWithUsername(String username, Long productId);
	public void saveCartItemWithUserId(CartItemEntity cartItemEntity);
	public CartEntity getCartByUserName(String userName);
//	public CartItemEntity getCartItemByCartAndProduct(CartEntity cartEntity, ProductEntity productEntity);
	public void saveCart(String userName);
	public void removeProduct(@Param ("productId") long productId , @Param("cartId") long cartId);
	public List<CartItemEntity> getListCartItem(long cartId);
	public void updateItemQuantity(CartItemEntity item);
	public void updateCartItem(CartItemEntity item);
	public void removeCartItems(CartItemEntity item);
	public void saveWithCartCode(CartEntity cartEntity);
	public CartEntity getCartByCartCode(String cartCode);
	public void saveCartItemWithCartCodeAndProductId(CartItemEntity cartItemEntity);
	public List<CartItemEntity> getListCartItemWithCartCode(String cartCode);
	public CartItemEntity getCartItemByCartCodeAndProduct(String cartCode, Long productId);
	public CartItemEntity getCartItemByCartIdAndProduct(long cartId, Long productId);
	public void saveCartItemWithCartIdAndProductId(CartItemEntity cartItemEntity);
	public void updateCartItemQuantityWithCartId(CartItemEntity cartItemEntity);
	public void updateCartItemQuantityWithCartCode(CartItemEntity cartItemEntity);
	

		
	
}