//package com.yehuda.coupons.api;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.yehuda.coupons.beans.Income;
//import com.yehuda.coupons.logic.IncomeService;
//
//@RestController
//@RequestMapping("/income")
//public class IncomeApi {
//
//	@Autowired
//	private IncomeService incomeController;
//	
//	
//	@GetMapping
//	public Income getAllIncome() {
//		return incomeController.getAllIncome();
//	}
//	
////	@GetMapping("/company-income/{companyId}")
////	public Income getIncomeByCompany(@PathVariable("companyId") long companyId) {
////		return incomeController.getIncomeByCompany(companyId);
////	}
//	
//	@GetMapping("/customer-income/{customerId}")
//	public Income getIncomeByCustomer(@PathVariable("customerId") long customerId) {
//		return incomeController.getIncomeByCustomer(customerId);
//	}
//	
//}
