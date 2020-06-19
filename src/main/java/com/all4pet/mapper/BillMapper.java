package com.all4pet.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.all4pet.entity.BillEntity;


@Mapper
public interface BillMapper {

	void saveBill(BillEntity bill);
	void saveBillItems(String billCode, long productId) ;
	long getBillId();

}