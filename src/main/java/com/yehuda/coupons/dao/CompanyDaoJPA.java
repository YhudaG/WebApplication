package com.yehuda.coupons.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yehuda.coupons.beans.CompanyEntity;
import com.yehuda.coupons.enums.ErrorType;
import com.yehuda.coupons.exceptions.ApplicationException;

@Repository
public class CompanyDaoJPA implements ICompanyDao {

	@PersistenceContext(unitName = "coupon_DB")
	private EntityManager entityManager;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createCompany(CompanyEntity company) throws ApplicationException {

		entityManager.persist(company);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public CompanyEntity getCompanyById(long companyId) {
		
		CompanyEntity company = entityManager.find(CompanyEntity.class, companyId);
		return company;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateCompany(CompanyEntity company) {

		entityManager.merge(company);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCompanyById(long companyId) {

		CompanyEntity company = getCompanyById(companyId);
		entityManager.remove(company);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean companyNameExist(String companyName) throws ApplicationException {
		TypedQuery<CompanyEntity> query = entityManager.createQuery("FROM CompanyEntity company WHERE company.companyName = ?", CompanyEntity.class);
		query.setParameter(1, companyName);
		try {
			query.getSingleResult();
			return true;

		} catch (NoResultException e) {
			return false;

		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CompanyEntity> getAllCompanies() {
		TypedQuery<CompanyEntity> query = entityManager.createQuery("FROM CompanyEntity company ORDER BY company.companyName", CompanyEntity.class);
		List<CompanyEntity> companies = query.getResultList();
		return companies;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public long login(String companyName, String password) throws ApplicationException {
		TypedQuery<CompanyEntity> query = entityManager.createQuery("FROM CompanyEntity company WHERE company.companyName=? AND company.password=?",
				CompanyEntity.class);
		query.setParameter(1, companyName);
		query.setParameter(2, password);
		try {
			return query.getSingleResult().getCompanyId();

		} catch (NoResultException e) {
			throw new ApplicationException(ErrorType.LOGIN_ERROR);

		}
	}

}
