package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Account;
import com.example.demo.service.AccountService;
import com.example.demp.dto.TransactionDto;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.register(account));
    }
	
	@PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        return ResponseEntity.ok(accountService.login(account.getUsername(), account.getPassword()));
    }
	
	@GetMapping("/username/{username}")
    public ResponseEntity<Account> getUserByUsername(@PathVariable String username) {
        Account account = accountService.findUserByUsername(username);
        if(account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
	
	@PostMapping("/send")
    public ResponseEntity<String> sendMoney(@RequestBody TransactionDto transactionDto){
        try {
            accountService.sendMoney(transactionDto);
            return ResponseEntity.ok("Transaction successful");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
	
	@PostMapping("/request")
    public ResponseEntity<String> requestMoney(@RequestParam String requesterUsername, @RequestParam String giverUsername, @RequestParam int amount){
        try {
            accountService.requestMoney(requesterUsername, giverUsername, amount);
            return ResponseEntity.ok("Transaction successful");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
	
	@GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccounts() {
        try {
            List<Account> accounts = accountService.getAllAccounts();
            return ResponseEntity.ok(accounts);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
