package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Account;
import com.example.demo.models.Transaction;
import com.example.demo.service.AccountService;
import com.example.demo.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) throws Exception {
        Account senderUser = accountService.findUserById(transaction.getSender().getId());
        Account receiverUser = accountService.findUserById(transaction.getReceiver().getId());

        if (senderUser == null || receiverUser == null) {
            throw new Exception("Sender or Receiver not found");
        }

        Transaction newTransaction = transactionService.createTransaction(senderUser, receiverUser, transaction.getAmount());
        return ResponseEntity.ok(newTransaction);
    }
	
	@GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
	
	@PostMapping("/send")
    public ResponseEntity<Transaction> sendMoney(@RequestBody Transaction transaction) {
        try {
            Transaction newTransaction = transactionService.sendMoney(transaction.getSender().getId(), transaction.getReceiver().getId(), transaction.getAmount());
            return ResponseEntity.ok(newTransaction);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
