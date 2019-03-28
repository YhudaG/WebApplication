package com.yehuda.coupons.logic;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yehuda.coupons.beans.CompanyEntity;
import com.yehuda.coupons.beans.CouponEntity;
import com.yehuda.coupons.beans.CustomerEntity;
import com.yehuda.coupons.dao.ICompanyDao;
import com.yehuda.coupons.dao.ICouponDao;
import com.yehuda.coupons.dao.ICustomerDao;
import com.yehuda.coupons.enums.CouponType;
import com.yehuda.coupons.enums.ErrorType;
import com.yehuda.coupons.exceptions.ApplicationException;

@Controller
public class CouponController {
	
	@Autowired
	private ICouponDao couponDao;
	
	@Autowired
	private ICustomerDao customerDao;
	
	@Autowired
	private ICompanyDao companyDao;

	private static final int ONE_DAY = 1000 * 60 * 60 * 24;
	
	/**
	 * add the coupon to the current company in the system after checking that the
	 * title of coupon not exist in the system.
	 * 
	 * @param coupon
	 * @param companyId 
	 * @throws ApplicationException
	 */
	public void createCoupon(CouponEntity coupon, long companyId) throws ApplicationException {
		if (createCouponValidation(coupon)) {
			coupon.setCompany(new CompanyEntity(companyId));
			couponDao.createCoupon(coupon);
		}
	}

	/**
	 * delete coupon from the system by id.
	 * 
	 * @param couponId
	 * @throws ApplicationException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCouponById(long couponId, long companyId) throws ApplicationException {

		CouponEntity coupon = couponDao.getCouponById(couponId);
		
		// check if the coupon is of this company
		if (companyId != coupon.getCompany().getCompanyId()) {
			throw new ApplicationException(ErrorType.COMPANY_COUPON_ERROR);
		}

		// check if the coupon exist before delete
		if (!couponDao.isCouponIdExist(couponId)) {
			throw new ApplicationException(ErrorType.COUPON_ID_NOT_EXIST);
		}
		
		couponDao.deleteCouponById(couponId);
	}
	
	public void deleteExpiredCoupons() throws ApplicationException {

		couponDao.deleteExpiredCoupons();
		
	}

	public List<CouponEntity> getAllCoupons() throws ApplicationException {

		return couponDao.getAllCoupons();
	}

	public CouponEntity getCouponById(long couponId) throws ApplicationException {

		return couponDao.getCouponById(couponId);
	}


	public void updateCoupon(CouponEntity coupon) throws ApplicationException {

		// take the original coupon from the system for updating
		// if the coupon not exist it will throw exception.
		CouponEntity originalCoupon = couponDao.getCouponById(coupon.getCouponId());

		Date endDate = coupon.getEndDate();
		// check witch of the parameters to update and if there are correctly.
		if (endDate != null) {
			Date yesterday = new Date(new Date().getTime() - ONE_DAY);
			if (endDate.before(yesterday)) {
				throw new ApplicationException(ErrorType.COUPON_END_DATE_ERROR);
			}
			originalCoupon.setEndDate(endDate);
		}
		if (coupon.getPrice() > 0) {
			originalCoupon.setPrice(coupon.getPrice());
		}

		couponDao.updateCoupon(originalCoupon);
	}

	public List<CouponEntity> getCompanyCoupons(long companyId) throws ApplicationException {
		
		if (companyDao.getCompanyById(companyId) == null) {
			throw new ApplicationException(ErrorType.COMPANY_ID_NOT_EXIST);
		}
		
		return couponDao.getCompanyCoupons(companyId);
	}
	
	/**
	 * return all coupons that exist in the current type.
	 * 
	 * @param couponTtype
	 * @return Set of coupons
	 * @throws ApplicationException
	 */
	public List<CouponEntity> getCompanyCouponsByType(long companyId, CouponType couponTtype) throws ApplicationException {

		// check if the company exist in the system
		if (companyDao.getCompanyById(companyId)==null) {
			throw new ApplicationException(ErrorType.COMPANY_ID_NOT_EXIST);
		}

		return couponDao.getCompanyCouponsByType(companyId, couponTtype);
	}

	/**
	 * return all coupons that exist in the current price.
	 * 
	 * @param price
	 * @return Set of coupons
	 * @throws ApplicationException
	 */
	public List<CouponEntity> getCompanyCouponsByPrice(long companyId, double price) throws ApplicationException {

		// check if the company exist in the system
		if (companyDao.getCompanyById(companyId)==null) {
			throw new ApplicationException(ErrorType.COMPANY_ID_NOT_EXIST);
		}

		return couponDao.getCompanyCouponsByPrice(companyId, price);
	}

	/**
	 * return all coupons that exist in the current end date.
	 * 
	 * @param date
	 * @return Set of coupons
	 * @throws ApplicationException
	 */
	public List<CouponEntity> getCompanyCouponsByDate(long companyId, Date date) throws ApplicationException {

		// check if the company exist in the system
		if (companyDao.getCompanyById(companyId)==null) {
			throw new ApplicationException(ErrorType.COMPANY_ID_NOT_EXIST);
		}

		return couponDao.getCompanyCouponsByDate(companyId, date);

	}

	/**
	 * add the current coupon to the customer's coupons list in the system after
	 * checking that the current customer didn't purchase this coupon before. update
	 * the amount of this coupon.
	 * 
	 * @throws ApplicationException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void purchaseCoupon(long customerId, long couponId) throws ApplicationException {

		CouponEntity coupon = couponDao.getCouponById(couponId);

		// check if this coupon not finished
		Date yesterday = new Date(new Date().getTime() - ONE_DAY);
		if (coupon.getAmount() > 0 && coupon.getEndDate().after(yesterday)) {

			// check if this customer didn't buy this coupon before.
			if (couponDao.isCustomerPurchase(customerId, couponId)) {
				throw new ApplicationException(ErrorType.PURCHASE_AGAIN_ERROR);
			}

			CustomerEntity customer = customerDao.getCustomerById(customerId);

			if (customer==null) {
				throw new ApplicationException(ErrorType.CUSTOMER_ID_NOT_EXIST);
			}
			
			coupon.setAmount(coupon.getAmount() - 1);
			coupon.getCustomers().add(customer);
			couponDao.updateCoupon(coupon);
			
			return;
		}
		throw new ApplicationException(ErrorType.COUPOM_FINISHED_ERROR);
	}

	/**
	 * return all customer's coupons by the current type.
	 * 
	 * @param type
	 * @return Set of coupons
	 * @throws ApplicationException
	 */
	public List<CouponEntity> getCustomerCouponsByType(long customerId, CouponType type) throws ApplicationException {

		// check if the customer exist in the system
		if (customerDao.getCustomerById(customerId)==null) {
			throw new ApplicationException(ErrorType.CUSTOMER_ID_NOT_EXIST);
		}

		return couponDao.getCustomerCouponsByType(customerId, type);
	}

	/**
	 * return all customer's coupons by the current price.
	 * 
	 * @param customerId
	 * @param price
	 * @return Set of coupons
	 * @throws ApplicationException
	 */
	public List<CouponEntity> getCustomerCouponsByPrice(long customerId, double price) throws ApplicationException {

		// check if the customer exist in the system
		if (customerDao.getCustomerById(customerId)==null) {
			throw new ApplicationException(ErrorType.CUSTOMER_ID_NOT_EXIST);
		}

		return couponDao.getCustomerCouponsByPrice(customerId, price);
	}

	/**
	 * return all customer's coupons.
	 * 
	 * @return Set of coupons
	 * @throws ApplicationException
	 */
	public List<CouponEntity> getCustomerCoupons(long customerId) throws ApplicationException {

		return couponDao.getCustomerCoupons(customerId);
	}

	// return only the coupons that the current customer can buy
	public List<CouponEntity> getCustomerNotPurchased(long customerId) throws ApplicationException {

		// check if the customer exist in the system
		if (customerDao.getCustomerById(customerId)==null) {
			throw new ApplicationException(ErrorType.CUSTOMER_ID_NOT_EXIST);
		}

		return couponDao.getCustomerNotPurchased(customerId);
	}

	/**
	 * validate the coupon's requires parameters exist and if they are correct.
	 * 
	 * @param coupon
	 * @return
	 * @throws ApplicationException
	 */
	private boolean createCouponValidation(CouponEntity coupon) throws ApplicationException {

		String couponTitle = coupon.getTitle();
		if (couponTitle == null || coupon.getType() == null) {
			throw new ApplicationException(ErrorType.PARAMETER_NULL_ERROR);
		}

		// check the title is not empty.
		if (couponTitle.startsWith(" ")) {
			throw new ApplicationException(ErrorType.TITLE_WRONG_ERROR);
		}

		if (couponDao.isCouponTitleExist(couponTitle)) {
			throw new ApplicationException(ErrorType.COUPON_TITLE_EXIST_ERROR);
		}

		Date yesterday = new Date(new Date().getTime() - ONE_DAY);
		if (coupon.getStartDate().before(yesterday)) {
			throw new ApplicationException(ErrorType.COUPON_START_DATE_ERROR);
		}

		if (coupon.getEndDate().before(coupon.getStartDate())) {
			throw new ApplicationException(ErrorType.COUPON_END_DATE_ERROR);
		}

		if (coupon.getAmount() < 1) {
			throw new ApplicationException(ErrorType.COUPON_AMOUNT_ERRROR);
		}

		if (coupon.getPrice() <= 0) {
			throw new ApplicationException(ErrorType.COUPON_PRICE_ERRROR);
		}

		// message and image are not requires

		return true;
	}
	
}
