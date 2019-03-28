package com.yehuda.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yehuda.coupons.beans.CouponEntity;
import com.yehuda.coupons.beans.CustomerEntity;
import com.yehuda.coupons.dao.ICouponDao;
import com.yehuda.coupons.dao.ICustomerDao;
import com.yehuda.coupons.enums.ErrorType;
import com.yehuda.coupons.exceptions.ApplicationException;

@Controller
public class CustomerController {

	@Autowired
	private ICustomerDao customerDao;

	@Autowired
	private ICouponDao couponDao;

	public CustomerController() {
		super();
	}

	/**
	 * add a customer in the system after checking that the name of customer not
	 * exist.
	 * 
	 * @param customer
	 * @throws ApplicationException
	 */
	public void createCustomer(CustomerEntity customer) throws ApplicationException {

		if (createCustomerValidation(customer)) {
			customerDao.createCustomer(customer);
		}
	}

	/**
	 * delete a customer from the system and delete all customer's coupons.
	 * 
	 * @param customer
	 * @throws ApplicationException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCustomerById(long customerId) throws ApplicationException {

		CustomerEntity customer = getCustomerById(customerId);

		// check if the customer exist in the system
		if (customer == null) {
			throw new ApplicationException(ErrorType.CUSTOMER_ID_NOT_EXIST);
		}

		List<CouponEntity> coupons = customer.getCoupons();
		for (CouponEntity coupon : coupons) {
			coupon.getCustomers().remove(customer);
			couponDao.updateCoupon(coupon);
		}

		customerDao.deleteCustomerById(customerId);

	}

	/**
	 * update the current customer.
	 * 
	 * @param customer
	 * @throws ApplicationException
	 */
	public void updateCustomer(CustomerEntity customer) throws ApplicationException {

		// take the original customer from the system for updating
		// if the customer not exist it will throw exception.
		CustomerEntity originalCustomer = customerDao.getCustomerById(customer.getCustomerId());

		if (originalCustomer == null) {
			throw new ApplicationException(ErrorType.CUSTOMER_ID_NOT_EXIST);
		}

		String password = customer.getPassword();

		// check if there is a password to update
		if (password != null && !password.startsWith(" "))
			originalCustomer.setPassword(password);
		customerDao.updateCustomer(originalCustomer);
	}

	/**
	 * return a customer by id.
	 * 
	 * @param customerId
	 * @return Customer
	 * @throws ApplicationException
	 */
	public CustomerEntity getCustomerById(long customerId) throws ApplicationException {
		return customerDao.getCustomerById(customerId);
	}

	/**
	 * return all customers that exist in the system.
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public List<CustomerEntity> getAllCustomers() throws ApplicationException {
		return customerDao.getAllCustomeres();
	}

	public long login(String customerName, String password) throws ApplicationException {
		return customerDao.login(customerName, password);
	}

	private boolean createCustomerValidation(CustomerEntity customer) throws ApplicationException {

		String customerName = customer.getCustomerName();
		String password = customer.getPassword();

		// check if the requires parameters exist and if they are correct.
		if (customerName == null || password == null) {
			throw new ApplicationException(ErrorType.PARAMETER_NULL_ERROR);
		}

		// check minimum characters for password
		if (password.length() < 4) {
			throw new ApplicationException(ErrorType.PASSWORD_SHORT_ERROR);
		}

		// check the parameters are not empty.
		if (customerName.startsWith(" ") || password.startsWith(" ")) {
			throw new ApplicationException(ErrorType.WRONG_INPUT_ERROR);
		}

		// check if the name of customer already exist in the system
		if (customerDao.customerNameExist(customerName)) {
			throw new ApplicationException(ErrorType.NAME_EXIST_ERROR);
		}

		return true;

	}
}
