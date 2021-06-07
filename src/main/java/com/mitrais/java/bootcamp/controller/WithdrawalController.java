package com.mitrais.java.bootcamp.controller;

import com.mitrais.java.bootcamp.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/withdrawal")
public class WithdrawalController {
	//index screen
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView getIndex() {
		Transaction trx = new Transaction();
		return new ModelAndView("fixedWithdrawal", "form", trx);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView getIndex(Transaction trx, HttpServletRequest request) {
		//return ("redirect:/withdrawal/other");
		//return new ModelAndView("OtherWithdrawal", "form", trx);
		request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		return new ModelAndView("redirect:/withdrawal/other");
	}

	@RequestMapping(value = "/other", method = RequestMethod.GET)
	public ModelAndView getOtherAmountScreen(Transaction trxx) {
		Transaction trx = new Transaction();
		return new ModelAndView("OtherWithdrawal", "form", trx);
	}

	@RequestMapping(value = "/other", method = RequestMethod.POST)
	public ModelAndView submitOtherAmountScreen(Transaction trxx) {
		Transaction trx = new Transaction();
		return new ModelAndView("OtherWithdrawal", "form", trx);
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public ModelAndView confirm(@RequestParam String amt) {
		return new ModelAndView("ConfirmWithdrawal", "message", amt);
	}

}
