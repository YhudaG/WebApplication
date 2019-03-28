package com.yehuda.coupons.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yehuda.coupons.beans.CustomerEntity;
import com.yehuda.coupons.enums.ErrorType;
import com.yehuda.coupons.exceptions.ApplicationException;

@Repository
public class CustomerDaoJPA implements ICustomerDao {

	@PersistenceContext(unitName = "coupon_DB")
	private EntityManager entityManager;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createCustomer(CustomerEntity customer) throws ApplicationException {
		entityManager.persist(customer);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCustomerById(long customerId) throws ApplicationException {
		CustomerEntity customer = getCustomerById(customerId);
		entityManager.remove(customer);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateCustomer(CustomerEntity customer) throws ApplicationException {
		entityManager.merge(customer);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean customerNameExist(String customerName) throws ApplicationException {
		TypedQuery<CustomerEntity> query = entityManager.createQuery("FROM CustomerEntity customer WHERE customer.customerName = ?",
				CustomerEntity.class);
		query.setParameter(1, customerName);
		try {
			query.getSingleResult();
			return true;

		} catch (NoResultException e) {
			return false;

		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public CustomerEntity getCustomerById(long customerId) throws ApplicationException {
		return entityManager.find(CustomerEntity.class, customerId);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CustomerEntity> getAllCustomeres() throws ApplicationException {
		TypedQuery<CustomerEntity> query = entityManager.createQuery("FROM CustomerEntity customer ORDER BY customer.customerName", CustomerEntity.class);
		return query.getResultList();

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public long login(String custName, String password) throws ApplicationException {
		TypedQuery<CustomerEntity> query = entityManager
				.createQuery("FROM CustomerEntity customer WHERE customer.customerName=? AND customer.password=?", CustomerEntity.class);
		query.setParameter(1, custName);
		query.setParameter(2, password);
		try {
			return query.getSingleResult().getCustomerId();

		} catch (NoResultException e) {
			throw new ApplicationException(ErrorType.LOGIN_ERROR);

		}

	}
}
