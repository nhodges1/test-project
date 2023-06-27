package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Account;
import com.example.demo.models.Transaction;
import com.example.demo.repo.AccountRepo;
import com.example.demo.repo.TransactionRepo;

@Service
public class TransactionService {

	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	@Autowired
	private AccountService accountService;
	
	public Transaction createTransaction(Account sender, Account receiver, int amount) throws Exception{
        if(sender.getBalance() < amount){
            throw new Exception("Insufficient funds");
        }

        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(amount);

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountService.updateUser(sender);
        accountService.updateUser(receiver);

        return transactionRepo.save(transaction);
    }
	
	public Transaction sendMoney(Long senderId, Long receiverId, int amount) throws Exception {
        Account sender = accountRepo.findById(senderId).orElseThrow(() -> new Exception("Sender not found."));
        Account receiver = accountRepo.findById(receiverId).orElseThrow(() -> new Exception("Receiver not found."));

        if(sender.getBalance() < amount) {
            throw new Exception("Account has insufficient funds.");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(amount);

        sender.getSentTransactions().add(transaction);
        receiver.getReceivedTransactions().add(transaction);

        accountRepo.save(sender);
        accountRepo.save(receiver);

        return transactionRepo.save(transaction);
    }

	public List<Transaction> getAllTransactions(){
        return transactionRepo.findAll();
    }
}
