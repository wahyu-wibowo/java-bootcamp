package com.mitrais.java.bootcamp.controller;

import java.util.List;

import com.mitrais.java.bootcamp.model.persistence.Account;
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
	private TransactionService service;

	//TODO: SHOULD BE UNUSED
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
	public ModelAndView getIndex() {
		return new ModelAndView("index");
	}

	//welcome screen
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView welcomeScreen() {
		return new ModelAndView("transactionWelcome", "form", new Account());
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.POST)
	public String welcomeScreen(Account account) {
		try {
			service.checkAuth(account);
			return ("redirect:/transaction?acc=".concat(account.getAccountNumber()));
		} catch (Exception e) {
			return ("redirect:/validation?message=".concat(e.getMessage()));
		}
	}

	//choose transaction screen
	@RequestMapping(value = "/transaction", method = RequestMethod.GET)
	public ModelAndView chooseTransaction(@RequestParam String acc) {
		return new ModelAndView("TransactionChoose", "acc", acc);
	}

}
