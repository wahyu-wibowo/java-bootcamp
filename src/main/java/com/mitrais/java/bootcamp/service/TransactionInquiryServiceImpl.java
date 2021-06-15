package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.Constants;
import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.dto.TransactionInquiryData;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionInquiryServiceImpl implements TransactionInquiryService {
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public List<TransactionDto> inquiryTransaction(TransactionDto transactionDto) throws Exception {
		//inq both transfer and withdrawal
		if (!StringUtils.isEmpty(transactionDto.getAccount())) {
			return convertToDto(findAllTransactionDtoByAccount(transactionDto.getAccount()));
		} else if (!StringUtils.isEmpty(transactionDto.getDate())) {
			return convertToDto(findAllTransactionDtoByDate(transactionDto.getDate()));
		}
		return null;
	}

	protected List<TransactionInquiryData> findAllTransactionDtoByAccount(String acc) {
		return entityManager.createQuery("SELECT new com.mitrais.java.bootcamp.model.dto.TransactionInquiryData(" +
					"tx.account.accountNumber," +
					"tx.amount," +
					"tx.destinationAccount.accountNumber," +
					"tx.referenceNumber," +
					"tx.transactionDate  ) " +
				"FROM AbstractTransaction tx " +
				"WHERE tx.account.accountNumber = :acc AND tx.isConfirmed = true ORDER BY tx.transactionDate desc ", TransactionInquiryData.class)
				.setParameter("acc", acc)
				.setMaxResults(Constants.MAX_QUERY_BY_ACC_LIMIT)
				.getResultList();
	}

	protected List<TransactionInquiryData> findAllTransactionDtoByDate(String ddMMyy) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
		LocalDate dateTime = LocalDate.parse(ddMMyy, formatter);

		LocalDateTime before = dateTime.atStartOfDay();
		LocalDateTime after = dateTime.atTime(23,59, 59);

		return entityManager.createQuery("SELECT new com.mitrais.java.bootcamp.model.dto.TransactionInquiryData(" +
				"tx.account.accountNumber," +
				"tx.amount," +
				"tx.destinationAccount.accountNumber," +
				"tx.referenceNumber," +
				"tx.transactionDate  ) " +
				"FROM AbstractTransaction tx " +
				"WHERE tx.transactionDate >= :before AND tx.transactionDate < :after AND tx.isConfirmed = true ORDER BY tx.transactionDate desc ", TransactionInquiryData.class)
				.setParameter("before", before)
				.setParameter("after", after)
				.setMaxResults(Constants.MAX_QUERY_BY_ACC_LIMIT)
				.getResultList();
	}

	protected List<TransactionDto> convertToDto(List<TransactionInquiryData> trxs) {
		List<TransactionDto> result = new ArrayList<>();
		trxs.stream().forEach(trx -> {
			TransactionDto dto = new TransactionDto();
			dto.setDate(trx.getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
			dto.setAmount(trx.getAmount().toString());
			dto.setAccount(trx.getAccount());
			dto.setDestinationAccount(trx.getDestinationAccount());
			dto.setReferenceNumber(trx.getReferenceNumber());
			result.add(dto);
		});

		return result;
	}
}
