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
	public ModelAndView getIndex(@RequestParam String acc) {
		Transaction trx = new Transaction();
		return new ModelAndView("fixedWithdrawal", "acc", acc);
	}

	/*@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView getIndex(Transaction trx, HttpServletRequest request) {
		//return ("redirect:/withdrawal/other");
		//return new ModelAndView("OtherWithdrawal", "form", trx);
		request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		return new ModelAndView("redirect:/withdrawal/other");
	}*/

	//withdraw with custom amount
	@RequestMapping(value = "/other", method = RequestMethod.GET)
	public ModelAndView getOtherAmountScreen(@RequestParam String acc) {
		Transaction trx = new Transaction();
		trx.setAccount(acc);
		return new ModelAndView("OtherWithdrawal", "form", trx);
	}

	@RequestMapping(value = "/other", method = RequestMethod.POST)
	public ModelAndView submitOtherAmountScreen(Transaction trx) {
		return new ModelAndView("OtherWithdrawal", "form", trx);
	}

	//withdrawal confirmation
	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public ModelAndView confirm(@RequestParam String amt, @RequestParam String acc) {
		return new ModelAndView("ConfirmWithdrawal", "message", amt);
	}

}
