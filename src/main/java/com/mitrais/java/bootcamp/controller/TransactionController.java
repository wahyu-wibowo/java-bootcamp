package com.mitrais.java.bootcamp.controller;

import com.mitrais.java.bootcamp.Constants;
import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.Account;
import com.mitrais.java.bootcamp.service.AccountService;
import com.mitrais.java.bootcamp.service.TransactionInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TransactionController {
	@Autowired
	private TransactionInquiryService service;

	@Autowired
	private AccountService accountService;

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
			accountService.checkAuth(account);
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


	//inquiry trx
	@RequestMapping(value = "/transaction/inquiry", method = RequestMethod.GET)
	public ModelAndView getOtherAmountScreen(@RequestParam(required=false) String param) {
		//if param acc, then show acc inq
		if (Constants.PARAM_ACCOUNT.equals(param)) {
			return new ModelAndView("InquiryTrxByAccount", "form", new TransactionDto());
		} else if (Constants.PARAM_DATE.equals(param)) {
			return new ModelAndView("InquiryTrxByDate", "form", new TransactionDto());
		} else {
			//default error screen
			return new ModelAndView("validationMessage", "message", "incorrect parameter");
		}
	}

	@RequestMapping(value = "/transaction/inquiry", method = RequestMethod.POST)
	public ModelAndView submitOtherAmountScreen(TransactionDto trx) {
		try {
			List<TransactionDto> transactions = service.inquiryTransaction(trx);
			return new ModelAndView("InquiryTrxResult", "trxs", transactions);
		} catch (Exception e) {
			return new ModelAndView("redirect:/validation?message=".concat(e.getMessage()));
		}
	}

}
