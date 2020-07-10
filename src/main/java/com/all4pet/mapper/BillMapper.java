package com.all4pet.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.all4pet.entity.BillEntity;


@Mapper
public interface BillMapper {

	void saveBill(BillEntity bill);
	void saveBillItems(String billCode, long productId) ;
	long getBillId();
	List<BillEntity> getListBillByUserName(String userName);
	List<BillEntity> getOrderList();
	void deleteOrderById(long id);
	void updateOrder(long id, String receiver, String address, String phonenumber, int status, String paymentMethod,
			float totalMoney);
	BillEntity getBillById(long id);

//	void updateBillItemByBillCode(String billCode, long productId);

}