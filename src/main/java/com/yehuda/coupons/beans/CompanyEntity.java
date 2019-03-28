package com.yehuda.coupons.beans;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "companies")
public class CompanyEntity {
	
	@Id
	@GeneratedValue
	@Column(name = "CompanyId")
	private long companyid;
	
	@Column(name = "CompanyName", nullable = false)
	private String companyName;
	
	@Column(name = "Password", nullable = false)
	private String password;
	
	@Column(name = "Email", nullable = false)
	private String email;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="company", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CouponEntity> coupons;
	
	public CompanyEntity() {
		super();
	}
	
	


	public CompanyEntity(long companyid) {
		super();
		this.companyid = companyid;
	}




	public CompanyEntity(long companyid, String companyName, String password, String email) {
		super();
		this.companyid = companyid;
		this.companyName = companyName;
		this.password = password;
		this.email = email;
	}


	public CompanyEntity(String companyName, String password, String email) {
		super();
		this.companyName = companyName;
		this.password = password;
		this.email = email;
	}


	public long getCompanyId() {
		return companyid;
	}

	public void setCompanyId(long id) {
		this.companyid = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String compName) {
		this.companyName = compName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<CouponEntity> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<CouponEntity> coupons) {
		this.coupons = coupons;
	}


	@Override
	public String toString() {
		return "Company [companyid=" + companyid + ", companyName=" + companyName + ", password=" + password
				+ ", email=" + email + ", coupons=" + coupons + "]";
	}



}
