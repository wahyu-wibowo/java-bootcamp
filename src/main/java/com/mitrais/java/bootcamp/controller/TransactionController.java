package com.mitrais.java.bootcamp.controller;

import java.util.List;

import com.mitrais.java.bootcamp.service.EmployeeRepository;
import com.mitrais.java.bootcamp.model.Account;
import com.mitrais.java.bootcamp.model.Employee;
import com.mitrais.java.bootcamp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TransactionController {

	@Autowired
	private EmployeeRepository employeeData;

	@Autowired
	private TransactionService service;

	@RequestMapping(value = "/addNewEmployee", method = RequestMethod.POST)
	public String newEmployee(Employee employee) {

		employeeData.save(employee);
		return ("redirect:/listEmployees");

	}

	@RequestMapping(value = "/addNewEmployee", method = RequestMethod.GET)
	public ModelAndView addNewEmployee() {

		Employee emp = new Employee();
		return new ModelAndView("newEmployee", "form", emp);

	}

	@RequestMapping(value = "/listEmployees", method = RequestMethod.GET)
	public ModelAndView employees() {
		List<Employee> allEmployees = employeeData.findAll();
		return new ModelAndView("allEmployees", "employees", allEmployees);
	}


	//account list screen
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public ModelAndView getAccounts() {
		return new ModelAndView("allAccounts", "accounts", service.getAllAccount());
	}

	//error page
	@RequestMapping(value = "/validation", method = RequestMethod.GET)
	public ModelAndView error(@RequestParam String message) {
		return new ModelAndView("validationMessage", "message", message);
	}

	//index screen
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView getindex() {
		return new ModelAndView("index");
	}

	//welcome screen
	@RequestMapping(value = "/welcome", method = RequestMethod.POST)
	public String welcomeScreen(Account account) {
		try {
			service.checkAuth(account);
			return ("redirect:/transaction/");
		} catch (Exception e) {
			return ("redirect:/validation?message=".concat(e.getMessage()));
		}
	}

	//choose transaction screen
	@RequestMapping(value = "/transaction", method = RequestMethod.GET)
	public ModelAndView chooseTransaction(Account account) {
		return new ModelAndView("TransactionChoose");
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView welcomeScreen() {
		Account acc = new Account();
		return new ModelAndView("transactionWelcome", "form", acc);
	}

}
