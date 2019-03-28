package com.yehuda.coupons.dao;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.yehuda.coupons.beans.CompanyEntity;
import com.yehuda.coupons.exceptions.ApplicationException;

@Controller
public interface ICompanyDao {

	/**
	 * add the current company to the system
	 * 
	 * @param company
	 * @throws ApplicationException
	 */
	void createCompany(CompanyEntity company) throws ApplicationException;

	/**
	 * delete the current company from the system.
	 * 
	 * @param companyId
	 * @throws ApplicationException
	 */
	void deleteCompanyById(long companyId) throws ApplicationException;

	/**
	 * update the current company in the system.
	 * 
	 * @param company
	 * @throws ApplicationException
	 */
	void updateCompany(CompanyEntity company) throws ApplicationException;

	/**
	 * return a company from the system by the id of the desired company.
	 * 
	 * @param id of company
	 * @return Company
	 * @throws ApplicationException
	 */
	CompanyEntity getCompanyById(long id) throws ApplicationException;

	/**
	 * return all companies from the system.
	 * 
	 * @return Set of Company
	 * @throws ApplicationException
	 */
	List<CompanyEntity> getAllCompanies() throws ApplicationException;


	/**
	 * check name and password of the company and do login if it true.
	 * 
	 * @param compName the name of company
	 * @param password of company
	 * @return true if the password is correctly, and false if it's not correctly.
	 * @throws ApplicationException
	 */
	long login(String compName, String password) throws ApplicationException;

	/**
	 * check if the name of the new company already exist in the system
	 * 
	 * @param companyName
	 * @return
	 * @throws ApplicationException
	 */
	boolean companyNameExist(String companyName) throws ApplicationException;


	
}
