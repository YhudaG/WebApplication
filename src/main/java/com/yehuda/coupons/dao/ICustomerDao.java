package com.yehuda.coupons.dao;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.yehuda.coupons.beans.CustomerEntity;
import com.yehuda.coupons.exceptions.ApplicationException;

@Controller
public interface ICustomerDao {
	
	/**
	 * add the current customer to the system
	 * 
	 * @param customer
	 * @throws ApplicationException
	 */
	void createCustomer(CustomerEntity customer) throws ApplicationException;

	/**
	 * delete the current customer from the system.
	 * 
	 * @param customer
	 * @throws ApplicationException
	 */
	void deleteCustomerById(long customerId) throws ApplicationException;

	/**
	 * update the current customer in the system.
	 * 
	 * @param customerId
	 * @throws ApplicationException
	 */
	void updateCustomer(CustomerEntity customer) throws ApplicationException;

	/**
	 * return a customer from the system by the id of the desired customer.
	 * 
	 * @param id of customer
	 * @throws ApplicationException
	 */
	CustomerEntity getCustomerById(long id) throws ApplicationException;

	/**
	 * return all customers from the system.
	 * 
	 * @return Set of Customer
	 * @throws ApplicationException
	 */
	List<CustomerEntity> getAllCustomeres() throws ApplicationException;

	/**
	 * check the password of the customer and do login if it true.
	 * 
	 * @param customerName the name of customer
	 * @param password of customer
	 * @return true if the password is correctly, and false if it's not correctly.
	 * @throws ApplicationException
	 */
	long login(String customerName, String password) throws ApplicationException;

	/**
	 * check if the name of the new customer already exist in the system
	 * 
	 * @param customerName
	 * @return
	 * @throws ApplicationException
	 */
	boolean customerNameExist(String customerName) throws ApplicationException;




}
