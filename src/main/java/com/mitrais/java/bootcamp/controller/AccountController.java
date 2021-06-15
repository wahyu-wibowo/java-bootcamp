package com.mitrais.java.bootcamp.controller;

import com.mitrais.java.bootcamp.model.dto.FileDto;
import com.mitrais.java.bootcamp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/account")
public class AccountController {
	@Autowired
	private AccountService service;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView getAccounts() {
		return new ModelAndView("allAccounts", "accounts", service.getAllAccount());
	}

	//upload csv screen
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView welcomeScreen() {
		return new ModelAndView("AddAccount", "form", new FileDto());
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String consumeCsv(FileDto fileDto) {
		try {
			//service.checkAuth(account);
			service.insertCsv(fileDto.getPath());
			return ("redirect:/");
		} catch (Exception e) {
			return ("redirect:/validation?message=".concat(e.getMessage()));
		}
	}
}
