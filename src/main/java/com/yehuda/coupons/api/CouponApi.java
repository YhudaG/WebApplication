package com.yehuda.coupons.api;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yehuda.coupons.beans.CouponEntity;
import com.yehuda.coupons.enums.CouponType;
import com.yehuda.coupons.exceptions.ApplicationException;
import com.yehuda.coupons.logic.CouponController;

@RestController
@RequestMapping("/coupon")
public class CouponApi {

	@Autowired
	private CouponController couponController;

	@PostMapping
	public void createCoupon(@RequestBody CouponEntity coupon, @CookieValue("userId") long companyId)
			throws ApplicationException {
		couponController.createCoupon(coupon, companyId);
	}

	@PostMapping("/purchaseCoupon")
	public void purchaseCoupon(@RequestBody long couponId, @CookieValue("userId") long customerId)
			throws ApplicationException {
		couponController.purchaseCoupon(customerId, couponId);
	}

	@DeleteMapping("/{couponId}")
	public void deleteCouponById(@PathVariable("couponId") long couponId, @CookieValue("userId") long companyId)
			throws ApplicationException {
		couponController.deleteCouponById(couponId, companyId);
	}

	@GetMapping("/{couponId}")
	public CouponEntity getCouponById(@PathVariable("couponId") long couponId) throws ApplicationException {
		return couponController.getCouponById(couponId);
	}

	@GetMapping("/companyCoupons")
	public List<CouponEntity> getCompanyCoupons(@CookieValue("userId") long companyId) throws ApplicationException {
		return couponController.getCompanyCoupons(companyId);
	}

	@GetMapping("/customerPurchased")
	public List<CouponEntity> getAllCustomerPurchased(@CookieValue("userId") long customerId)
			throws ApplicationException {
		return couponController.getCustomerCoupons(customerId);
	}

	@GetMapping("/customerNotPurchased")
	public List<CouponEntity> getCustomerNotPurchased(@CookieValue("userId") long customerId)
			throws ApplicationException {
		return couponController.getCustomerNotPurchased(customerId);
	}

	@GetMapping("/getCompanyCouponsByDate")
	public List<CouponEntity> getCompanyCouponsByDate(@CookieValue("userId") long companyId,
			@RequestParam("date") long date) throws ApplicationException {

		return couponController.getCompanyCouponsByDate(companyId, new Date(date));
	}

	@GetMapping("/getCompanyCouponsByPrice")
	public List<CouponEntity> getCompanyCouponsByPrice(@CookieValue("userId") long companyId,
			@RequestParam("couponPrice") double price) throws ApplicationException {
		return couponController.getCompanyCouponsByPrice(companyId, price);
	}

	@GetMapping("/getCompanyCouponsByType")
	public List<CouponEntity> getCompanyCouponsByType(@CookieValue("userId") long companyId,
			@RequestParam("couponType") CouponType couponType) throws ApplicationException {
		return couponController.getCompanyCouponsByType(companyId, couponType);
	}

	@GetMapping("/getCustomerCouponsByType")
	public List<CouponEntity> getCustomerCouponsByType(@CookieValue("userId") long customerId,
			@RequestParam("couponType") String couponType) throws ApplicationException {
		return couponController.getCustomerCouponsByType(customerId, CouponType.valueOf(couponType));
	}

	@GetMapping("/getCustomerCouponsByPrice")
	public List<CouponEntity> getCustomerCouponsByPrice(@CookieValue("userId") long customerId,
			@RequestParam("couponPrice") double price) throws ApplicationException {
		return couponController.getCustomerCouponsByPrice(customerId, price);
	}

	@PutMapping
	public void updateCoupon(@RequestBody CouponEntity coupon) throws ApplicationException {
		couponController.updateCoupon(coupon);
	}

	@GetMapping
	public List<CouponEntity> getAllCoupons() throws ApplicationException {
		return couponController.getAllCoupons();
	}

}
