package com.yehuda.coupons.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yehuda.coupons.beans.CustomerEntity;
import com.yehuda.coupons.exceptions.ApplicationException;
import com.yehuda.coupons.logic.CustomerController;

@RestController
@RequestMapping("/customer")
public class CustomerApi {

	@Autowired
	private CustomerController customerController;

	@PostMapping
	public void createCustomer(@RequestBody CustomerEntity customer) throws ApplicationException {
		customerController.createCustomer(customer);
	}

	@DeleteMapping("/{customerId}")
	public void deleteCustomerById(@PathVariable("customerId") long customerId) throws ApplicationException {
		customerController.deleteCustomerById(customerId);
	}

	@GetMapping("/{customerId}")
	public CustomerEntity getCustomerById(@PathVariable("customerId") long customerId) throws ApplicationException {
		return customerController.getCustomerById(customerId);
	}

	@GetMapping
	public List<CustomerEntity> getAllCustomers() throws ApplicationException {
		return customerController.getAllCustomers();
	}

	@PutMapping
	public void updateCustomer(@RequestBody CustomerEntity customer) throws ApplicationException {
		customerController.updateCustomer(customer);
	}

}
