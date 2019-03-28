package com.yehuda.coupons.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "customers")
public class CustomerEntity {

	@Id
	@GeneratedValue
	@Column(name = "customerId")
	private long customerId;
	
	@Column(name = "customerName")
	private String customerName;
	
	@Column(name = "password")
	private String password;

	@ManyToMany(fetch=FetchType.LAZY, mappedBy="customers")
	@JsonIgnore
	private List<CouponEntity> coupons;

	public CustomerEntity() {
		super();
	}

	public CustomerEntity(long customerId, String customerName, String password) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.password = password;
	}

	public CustomerEntity(String customerName, String password) {
		super();
		this.customerName = customerName;
		this.password = password;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long id) {
		this.customerId = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<CouponEntity> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<CouponEntity> coupons) {
		this.coupons = coupons;
	}
		
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", password=" + password + "]";
	}

}
