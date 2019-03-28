package com.yehuda.coupons.api;

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
import org.springframework.web.bind.annotation.RestController;

import com.yehuda.coupons.beans.CompanyEntity;
import com.yehuda.coupons.exceptions.ApplicationException;
import com.yehuda.coupons.logic.CompanyController;

@RestController
@RequestMapping("/company")
public class CompanyApi {

	@Autowired
	private CompanyController companyController;

	@PostMapping
	public void createCompany(@RequestBody CompanyEntity company) throws ApplicationException {
		companyController.createCompany(company);
	}

	@DeleteMapping("/{companyId}")
	public void deleteCompanyById(@PathVariable("companyId") long companyId) throws ApplicationException {
		companyController.deleteCompanyById(companyId);
	}

	@GetMapping("/company/{companyId}")
	public CompanyEntity getCompanyById(@PathVariable("companyId") long companyId) throws ApplicationException {
		return companyController.getCompanyById(companyId);
		
	}

	@GetMapping("/company")
	public CompanyEntity getCompanyByCookie(@CookieValue("userId") long companyId) throws ApplicationException {
		return companyController.getCompanyById(companyId);
	}

	@GetMapping
	public List<CompanyEntity> getAllCompanies() throws ApplicationException {
		return companyController.getAllCompanies();
	}

	@PutMapping
	public void updateCompany(@RequestBody CompanyEntity company) throws ApplicationException {
		companyController.updateCompany(company);
	}

}
