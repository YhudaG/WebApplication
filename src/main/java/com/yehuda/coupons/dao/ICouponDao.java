package com.yehuda.coupons.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.yehuda.coupons.beans.CouponEntity;
import com.yehuda.coupons.enums.CouponType;
import com.yehuda.coupons.exceptions.ApplicationException;

@Controller
public interface ICouponDao {

	/**
	 * add the current coupon to the system
	 * 
	 * @param coupon
	 * @throws ApplicationException
	 */
	void createCoupon(CouponEntity coupon) throws ApplicationException;

	/**
	 * delete coupon from the system by coupon id.
	 * 
	 * @param couponId
	 * @throws ApplicationException
	 */
	void deleteCouponById(long couponId) throws ApplicationException;

	/**
	 * update the current coupon in the system.
	 * 
	 * @param CouponEntity
	 * @throws ApplicationException
	 */
	void updateCoupon(CouponEntity coupon) throws ApplicationException;

	/**
	 * return a coupon from the system by the coupon id.
	 * 
	 * @param couponId
	 * @throws ApplicationException
	 */
	CouponEntity getCouponById(long couponId) throws ApplicationException;

	/**
	 * return all coupons from the system.
	 * 
	 * @return List Collection of coupons
	 * @throws ApplicationException
	 */
	List<CouponEntity> getAllCoupons() throws ApplicationException;


	/**
	 * return all company coupons by the current type
	 * 
	 * @param companyId
	 * @param couponType
	 * @return List Collection of coupons
	 * @throws ApplicationException
	 */
	List<CouponEntity> getCompanyCouponsByType(long companyId, CouponType couponType) throws ApplicationException;

	/**
	 * return all company coupons that cheaper from the current price.
	 * 
	 * @param companyId
	 * @param price
	 * @return List Collection of coupons
	 * @throws ApplicationException
	 */
	List<CouponEntity> getCompanyCouponsByPrice(long companyId, double price) throws ApplicationException;

	/**
	 * return all company coupons that their end date is before the current date
	 * 
	 * @param companyId
	 * @param date
	 * @return List Collection of coupons
	 * @throws ApplicationException
	 */
	List<CouponEntity> getCompanyCouponsByDate(long companyId, Date date) throws ApplicationException;

	/**
	 * check if the coupon title exist in the system
	 * 
	 * @param couponId
	 * @return boolean, true if title of coupon exist and false if it doesn't exist.
	 * @throws ApplicationException
	 */
	boolean isCouponTitleExist(String title) throws ApplicationException;



	/**
	 * check if the coupon exist in the system
	 * 
	 * @param couponId
	 * @return boolean, true if coupon exist and false if it doesn't exist.
	 * @throws ApplicationException
	 */
	boolean isCouponIdExist(long couponId) throws ApplicationException;

	/**
	 * delete all coupons that expired from the system (for the daily task)
	 * 
	 * @throws ApplicationException
	 */
	void deleteExpiredCoupons() throws ApplicationException;


	/**
	 * return the coupons that the current customer didn't purchase
	 * 
	 * @param customerId
	 * @return
	 * @throws ApplicationException
	 */
	List<CouponEntity> getCustomerNotPurchased(long customerId) throws ApplicationException;

	/**
	 * check if the current customer already purchased this coupon
	 * 
	 * @param customerId
	 * @param couponId
	 * @return
	 * @throws ApplicationException
	 */
	boolean isCustomerPurchase(long customerId, long couponId) throws ApplicationException;

	/**
	 * return customer coupons by coupon type
	 * 
	 * @param customerId
	 * @param types
	 * @return
	 * @throws ApplicationException
	 */
	List<CouponEntity> getCustomerCouponsByType(long customerId, CouponType types) throws ApplicationException;

	/**
	 * return customer coupons by coupon price
	 * 
	 * @param customerId
	 * @param types
	 * @return
	 * @throws ApplicationException
	 */
	List<CouponEntity> getCustomerCouponsByPrice(long customerId, double price) throws ApplicationException;

	List<CouponEntity> getCompanyCoupons(long companyId) throws ApplicationException;

	List<CouponEntity> getCustomerCoupons(long customerId) throws ApplicationException;


}
