package com.yehuda.coupons.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yehuda.coupons.enums.CouponType;

@Entity
@Table(name = "coupons")
public class CouponEntity {

	@Id
	@GeneratedValue
	@Column(name = "couponId")
	private long couponId;

	@Column(name = "title")
	private String title;

	@Column(name = "startDate")
	private Date startDate;

	@Column(name = "endDate")
	private Date endDate;

	@Column(name = "amount")
	private int amount;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private CouponType type;

	@Column(name = "message")
	private String message;

	@Column(name = "price")
	private double price;

	@Column(name = "image")
	private String image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="companyId")
	@JsonIgnore
	private CompanyEntity company;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(joinColumns = {@JoinColumn(name="couponId")}, inverseJoinColumns = { @JoinColumn(name="customerId")})
	@JsonIgnore
	private List<CustomerEntity> customers;

	public CouponEntity() {
		super();
	}

	public CouponEntity(long couponId, String title, Date startDate, Date endDate, int amount, CouponType type,
			String message, double price, String image) {
		super();
		this.couponId = couponId;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}

	public CouponEntity(String title, Date startDate, Date endDate, int amount, CouponType type, String message, double price,
			String image) {
		super();
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String massage) {
		this.message = massage;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public CompanyEntity getCompany() {
		return company;
	}

	public void setCompany(CompanyEntity company) {
		this.company = company;
	}
	
	

	public List<CustomerEntity> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerEntity> customers) {
		this.customers = customers;
	}

	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}


}
