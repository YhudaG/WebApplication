package com.yehuda.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yehuda.coupons.beans.CompanyEntity;
import com.yehuda.coupons.dao.ICompanyDao;
import com.yehuda.coupons.enums.ErrorType;
import com.yehuda.coupons.exceptions.ApplicationException;

@Controller
public class CompanyController {

	@Autowired
	private ICompanyDao companyDao;

	public CompanyController() {
		super();
	}

	/**
	 * add a company to the system after checking the name of company doesn't exist.
	 * 
	 * @param company
	 * @throws ApplicationException
	 */
	public void createCompany(CompanyEntity company) throws ApplicationException {

		if (createCompanyValidation(company)) {
			companyDao.createCompany(company);
		}
	}

	/**
	 * return all details of the current company
	 * 
	 * @return CompanyId
	 * @throws ApplicationException
	 */
	public CompanyEntity getCompanyById(long companyId) throws ApplicationException {
		CompanyEntity company = companyDao.getCompanyById(companyId);
		return company;
	}

	/**
	 * delete a company from system, and delete all company's coupons.
	 * 
	 * @param companyId
	 * @throws ApplicationException
	 */
	public void deleteCompanyById(long companyId) throws ApplicationException {

		if (companyDao.getCompanyById(companyId) == null) {
			throw new ApplicationException(ErrorType.COMPANY_ID_NOT_EXIST);
		}

		companyDao.deleteCompanyById(companyId);
	}

	/**
	 * update the current company.
	 * 
	 * @param company
	 * @throws ApplicationException
	 */
	public void updateCompany(CompanyEntity company) throws ApplicationException {

		// take the original company from the system for updating
		// if the company not exist it will throw exception.
		CompanyEntity originalCompany = companyDao.getCompanyById(company.getCompanyId());

		if (originalCompany == null) {
			throw new ApplicationException(ErrorType.COMPANY_ID_NOT_EXIST);
		}

		String email = company.getEmail();
		String password = company.getPassword();

		// check which of the parameters to update
		if (email != null && !email.startsWith(" ")) {
			originalCompany.setEmail(email);
		}
		if (password != null && !password.startsWith(" ")) {
			originalCompany.setPassword(password);
		}

		companyDao.updateCompany(originalCompany);
	}

	/**
	 * return all companies that exist in the system.
	 * 
	 * @return Set of all companies
	 * @throws ApplicationException
	 */
	public List<CompanyEntity> getAllCompanies() throws ApplicationException {
		return companyDao.getAllCompanies();
	}

	/**
	 * check the name and password for access, return the id of the user
	 * 
	 * @param companyName
	 * @param password
	 * @return
	 * @throws ApplicationException
	 */
	public long login(String companyName, String password) throws ApplicationException {

		return companyDao.login(companyName, password);
	}

	private boolean createCompanyValidation(CompanyEntity company) throws ApplicationException {

		String companyName = company.getCompanyName();
		String email = company.getEmail();
		String password = company.getPassword();

		// check if the requires parameters exist and if they are correct.
		if (companyName == null || password == null || email == null) {
			throw new ApplicationException(ErrorType.PARAMETER_NULL_ERROR);
		}

		// check minimum characters for password.
		if (password.length() < 4) {
			throw new ApplicationException(ErrorType.PASSWORD_SHORT_ERROR);
		}

		// check the parameters are not empty.
		if (companyName.startsWith(" ") || password.startsWith(" ") || email.startsWith(" ")) {
			throw new ApplicationException(ErrorType.WRONG_INPUT_ERROR);
		}

		// check if the name of company already exist in the system
		if (companyDao.companyNameExist(companyName)) {
			throw new ApplicationException(ErrorType.NAME_EXIST_ERROR);
		}
		return true;
	}

}
