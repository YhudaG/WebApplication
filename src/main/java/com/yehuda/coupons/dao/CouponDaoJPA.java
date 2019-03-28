package com.yehuda.coupons.dao;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yehuda.coupons.beans.CompanyEntity;
import com.yehuda.coupons.beans.CouponEntity;
import com.yehuda.coupons.enums.CouponType;
import com.yehuda.coupons.exceptions.ApplicationException;

@Repository
public class CouponDaoJPA implements ICouponDao {

	@PersistenceContext(unitName = "coupon_DB")
	private EntityManager entityManager;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createCoupon(CouponEntity coupon) throws ApplicationException {
		entityManager.persist(coupon);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCouponById(long couponId) throws ApplicationException {
		
		CouponEntity coupon = entityManager.find(CouponEntity.class, couponId);
		entityManager.remove(coupon);

	}

	// for the daily thread
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteExpiredCoupons() throws ApplicationException {
		
		Query query = entityManager.createQuery("DELETE FROM CouponEntity coupon WHERE coupon.endDate<?");
		query.setParameter(1, new Date(System.currentTimeMillis()));
		query.executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateCoupon(CouponEntity coupon) throws ApplicationException {
		entityManager.merge(coupon);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public CouponEntity getCouponById(long couponId) throws ApplicationException {
		return entityManager.find(CouponEntity.class, couponId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CouponEntity> getAllCoupons() throws ApplicationException {
		TypedQuery<CouponEntity> query = entityManager.createQuery("FROM CouponEntity coupon ORDER BY coupon.type", CouponEntity.class);
		return query.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CouponEntity> getCompanyCoupons(long companyId) throws ApplicationException {
		TypedQuery<CouponEntity> query = entityManager.createQuery("FROM CouponEntity coupon WHERE coupon.company=? ORDER BY coupon.type", CouponEntity.class);
		query.setParameter(1, entityManager.find(CompanyEntity.class, companyId));
		return query.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CouponEntity> getCompanyCouponsByType(long companyId, CouponType couponType) throws ApplicationException {
		TypedQuery<CouponEntity> query = entityManager.createQuery("FROM CouponEntity coupon WHERE coupon.company=? AND coupon.type=? ORDER BY coupon.type",
				CouponEntity.class);
		query.setParameter(1, entityManager.find(CompanyEntity.class, companyId));
		query.setParameter(2, couponType);
		return query.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CouponEntity> getCompanyCouponsByPrice(long companyId, double price) throws ApplicationException {
		TypedQuery<CouponEntity> query = entityManager.createQuery("FROM CouponEntity coupon WHERE coupon.company=? AND coupon.price<=? ORDER BY coupon.price",
				CouponEntity.class);
		query.setParameter(1, entityManager.find(CompanyEntity.class, companyId));
		query.setParameter(2, price);
		return query.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CouponEntity> getCompanyCouponsByDate(long companyId, java.util.Date date) throws ApplicationException {
		TypedQuery<CouponEntity> query = entityManager.createQuery("FROM CouponEntity coupon WHERE coupon.company=? AND coupon.endDate<=? ORDER BY coupon.endDate",
				CouponEntity.class);
		query.setParameter(1, entityManager.find(CompanyEntity.class, companyId));
		query.setParameter(2, new Date(date.getTime()));
		return query.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CouponEntity> getCustomerCoupons(long customerId) throws ApplicationException {
		TypedQuery<CouponEntity> query = entityManager.createQuery(
				"SELECT coupon FROM CouponEntity as coupon INNER JOIN coupon.customers as customer WHERE customer.customerId=? ORDER BY coupon.type",
				CouponEntity.class);
		query.setParameter(1, customerId);
		System.out.println(query.toString());
		return query.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CouponEntity> getCustomerCouponsByType(long customerId, CouponType type) throws ApplicationException {
		TypedQuery<CouponEntity> query = entityManager.createQuery(
				"SELECT coupon FROM CouponEntity as coupon INNER JOIN coupon.customers as customer WHERE customer.customerId=? AND "
						+ "coupon.type=? ORDER BY coupon.type",
				CouponEntity.class);
		query.setParameter(1, customerId);
		query.setParameter(2, type);
		return query.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CouponEntity> getCustomerCouponsByPrice(long customerId, double price) throws ApplicationException {
		TypedQuery<CouponEntity> query = entityManager.createQuery(
				"SELECT coupon FROM CouponEntity as coupon INNER JOIN coupon.customers as customer WHERE customer.customerId=? AND "
						+ "coupon.price=? ORDER BY coupon.price",
				CouponEntity.class);
		query.setParameter(1, customerId);
		query.setParameter(2, price);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CouponEntity> getCustomerNotPurchased(long customerId) throws ApplicationException {
		Query query = entityManager.createNativeQuery("SELECT * FROM coupons WHERE couponID NOT IN "
				+ "(SELECT couponID FROM coupons_customers WHERE customerId=?) ORDER BY type", CouponEntity.class);
		query.setParameter(1, customerId);
		return query.getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean isCustomerPurchase(long customerId, long couponId) throws ApplicationException {
		TypedQuery<CouponEntity> query = entityManager.createQuery(
				"SELECT coupon FROM CouponEntity as coupon INNER JOIN coupon.customers as customer WHERE customer.customerId=? AND "
						+ "coupon.couponId=?",
				CouponEntity.class);
		query.setParameter(1, customerId);
		query.setParameter(2, couponId);
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean isCouponTitleExist(String title) throws ApplicationException {
		TypedQuery<CouponEntity> query = entityManager.createQuery("FROM CouponEntity coupon WHERE coupon.title=?", CouponEntity.class);
		query.setParameter(1, title);
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean isCouponIdExist(long couponId) throws ApplicationException {
		TypedQuery<CouponEntity> query = entityManager.createQuery("FROM CouponEntity coupon WHERE coupon.couponId=?", CouponEntity.class);
		query.setParameter(1, couponId);
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

}
