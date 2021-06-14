package com.mitrais.java.bootcamp.controller;

import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.AbstractTransaction;
import com.mitrais.java.bootcamp.model.persistence.Transfer;
import com.mitrais.java.bootcamp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/transfer")
public class TransferController {
	@Autowired
	private TransactionService service;

	//index screen
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView getIndex(@RequestParam String acc) {
		TransactionDto trx = new TransactionDto();
		trx.setAccount(acc);
		return new ModelAndView("TransferForm", "form", trx);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView submitTransfer(TransactionDto trx) {
		try {
			TransactionDto transaction = service.createTransfer(trx.getAccount(), trx.getDestinationAccount(), trx.getAmount());

			return new ModelAndView("ConfirmTransfer", "trx", transaction);
		} catch (Exception e) {
			return new ModelAndView("redirect:/validation?message=".concat(e.getMessage()));
		}
	}

	@RequestMapping(value = "/confirmed", method = RequestMethod.GET)
	public ModelAndView confirmed(@RequestParam String id) {
		try {
			AbstractTransaction trx = service.confirmTransaction(id);
			return new ModelAndView("redirect:/transaction?acc=".concat(trx.getAccount().getAccountNumber()));
		} catch (Exception e) {
			return new ModelAndView("redirect:/validation?message=".concat(e.getMessage()));
		}
	}

}
