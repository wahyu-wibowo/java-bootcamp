package com.mitrais.java.bootcamp.controller;

import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.Transaction;
import com.mitrais.java.bootcamp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/withdrawal")
public class WithdrawalController {
	@Autowired
	private TransactionService service;

	//index screen
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView getIndex(@RequestParam String acc) {
		Transaction trx = new Transaction();
		return new ModelAndView("fixedWithdrawal", "acc", acc);
	}

	//withdraw with custom amount
	@RequestMapping(value = "/other", method = RequestMethod.GET)
	public ModelAndView getOtherAmountScreen(@RequestParam String acc) {
		TransactionDto trx = new TransactionDto();
		trx.setAccount(acc);
		return new ModelAndView("OtherWithdrawal", "form", trx);
	}

	@RequestMapping(value = "/other", method = RequestMethod.POST)
	public ModelAndView submitOtherAmountScreen(TransactionDto trx) {
		try {
			TransactionDto transaction = service.createTransaction(trx.getAccount(), trx.getAmount());
			return new ModelAndView("ConfirmWithdrawal", "trx", transaction);
		} catch (Exception e) {
			return new ModelAndView("redirect:/validation?message=".concat(e.getMessage()));
		}
	}

	//withdrawal confirmation
	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public ModelAndView confirm(@RequestParam String amt, @RequestParam String acc) {
		try {
			TransactionDto trx = service.createTransaction(acc, amt);
			return new ModelAndView("ConfirmWithdrawal", "trx", trx);
		} catch (Exception e) {
			return new ModelAndView("redirect:/validation?message=".concat(e.getMessage()));
		}
	}

	@RequestMapping(value = "/confirmed", method = RequestMethod.GET)
	public ModelAndView confirmed(@RequestParam String id) {
		try {
			Transaction trx = service.confirmTransaction(id);
			return new ModelAndView("redirect:/transaction?acc=".concat(trx.getAccount()));
		} catch (Exception e) {
			return new ModelAndView("redirect:/validation?message=".concat(e.getMessage()));
		}
	}

}
