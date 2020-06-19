
package com.all4pet.entity;

import java.util.Date;
import java.util.List;





//import com.jwatgroupb.validator.Phone;


public class BillEntity {

	

//	private List<BillDetailEntity> listBillDetails = new ArrayList<>();
	private long id;
	private UserEntity userEntity;
	private float totalMoney;
	private int status;
	private Date deliveryDate;	
	private String receiver;
	private String address;
	private String phoneNumber;
	private String paymentMethod;
	private String email;
	private String note;
	private Date billDate;
	private List<CartItemEntity> listItems;
	private String billCode;
//	private PayerEntity payerEntity;

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}


	public float getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(float totalMoney) {
		this.totalMoney = totalMoney;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhonenumber() {
		return phoneNumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phoneNumber = phonenumber;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date date) {
		this.billDate = date;
	}

	public List<CartItemEntity> getListItems() {
		return listItems;
	}

	public void setListItems(List<CartItemEntity> listItems) {
		this.listItems = listItems;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}


	

	
}
