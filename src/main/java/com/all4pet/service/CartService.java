

package com.all4pet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.all4pet.entity.CartEntity;
import com.all4pet.entity.CartItemEntity;
import com.all4pet.entity.ProductEntity;
import com.all4pet.mapper.CartMapper;
import com.all4pet.mapper.ProductMapper;
import com.all4pet.mapper.UserMapper;
import com.all4pet.entity.UserEntity;



@Service
public class CartService {

	@Autowired CartMapper cartMapper;
	@Autowired ProductMapper productMapper;
	@Autowired UserMapper userMapper;
	
	
	public CartEntity getCartByCartCode(String cartCode) {
		CartEntity cart = cartMapper.getCartByCartCode(cartCode);
		if (cart == null) {
			return new CartEntity();
		}
		List<CartItemEntity> listItem = cartMapper.getListCartItemWithCartCode(cartCode);
		cart.setListCartItem(listItem);
		cart.setCartCode(cartCode);
		return cart;
	}
	
	public CartEntity getCartByUserName(String userName) {
		UserEntity user = userMapper.getUserByUserName(userName);
		CartEntity cart = cartMapper.getCartByUserName(userName);
		List<CartItemEntity> listItem = cartMapper.getListCartItem(cart.getId());
		cart.setUserEntity(user);
		cart.setListCartItem(listItem);
		return cart;
	}


	// Them san pham cho khach da co gio hang chua dang nhap
	public void updateCartItem(CartEntity cartEntity, Long productId) {
		ProductEntity productEntity = productMapper.getProductById(productId);
		String cartCode = cartEntity.getCartCode();
		CartItemEntity cartItemEntity = cartMapper.getCartItemByCartCodeAndProduct(cartCode, productId);
		// Kiem tra co ton tai product trong cart
		if (cartItemEntity == null) {
			cartItemEntity = new CartItemEntity();
//			cartItemEntity.setCartEntity(cartEntity);
			cartItemEntity.setProductEntity(productEntity);
			cartItemEntity.setQuantity(1);
			cartItemEntity.setCartCode(cartCode);
			cartMapper.saveCartItemWithCartCodeAndProductId(cartItemEntity);
		} else {
			cartItemEntity.setProductEntity(productEntity);
			cartItemEntity.setQuantity(cartItemEntity.getQuantity() + 1);
			cartItemEntity.setCartCode(cartCode);
			cartMapper.updateCartItemQuantityWithCartCode(cartItemEntity);
		}
	}



	// Them san pham cho khach hang da dang nhap tai khoan
	public void updateOrCreateCartWithUsername(String userName, Long productId) {

		ProductEntity productEntity = productMapper.getProductById(productId);
		CartEntity cartEntity = cartMapper.getCartByUserName(userName);
		long cartId = cartEntity.getId();
		if (cartEntity != null) {// Ton tai cart
			CartItemEntity cartItemEntity = cartMapper.getCartItemByCartIdAndProduct(cartId,
					productId);
			// Kiem tra co ton tai product trong cart
			if (cartItemEntity == null) {
				cartItemEntity = new CartItemEntity();
				cartItemEntity.setProductEntity(productEntity);
				cartItemEntity.setQuantity(1);
				cartItemEntity.setCartId(cartEntity.getId());
				cartMapper.saveCartItemWithCartIdAndProductId(cartItemEntity);
			} else {
				cartItemEntity.setQuantity(cartItemEntity.getQuantity() + 1);
				cartItemEntity.setCartId(cartEntity.getId());
				cartItemEntity.setProductEntity(productEntity);
				cartMapper.updateCartItemQuantityWithCartId(cartItemEntity);
			}
		}
	}

	// Tao moi cart va them san pham cho khach chua dang nhap
	public void saveCartWithCartCodeAndProductId(String cartCode, Long productId) {
		CartEntity cartEntity = new CartEntity();
		cartEntity.setCartCode(cartCode);
		cartMapper.saveWithCartCode(cartEntity);
		updateCartItem(cartEntity,productId);
//		CartItemEntity cartItemEntity = new CartItemEntity();
//		cartItemEntity.setProductEntity(productEntity);
//		cartItemEntity.setQuantity(1);
//		cartItemEntity.setCartCode(cartCode);
//		cartMapper.saveCartItemWithCartCodeAndProductId(cartItemEntity);
	}


}