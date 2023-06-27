package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Account;
import com.example.demo.repo.AccountRepo;
import com.example.demp.dto.TransactionDto;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepo accountRepo;
	
	public Account register(Account account) {
		account.setBalance(10000);
		return accountRepo.save(account);
	}
	
	public Account login(String username, String password) {
		Account account = accountRepo.findByUsername(username);
		
		if(account != null && password.equals(account.getPassword())) {
			return account;
		} else {
			return null;
		}
	}
	
	public void sendMoney(Long senderId, Long receiverId, int amount) throws Exception {
		Account sender = accountRepo.findById(senderId).orElseThrow(() -> new Exception("Account not found."));
		Account receiver = accountRepo.findById(receiverId).orElseThrow(() -> new Exception("Account not found."));
		
		if(sender.getBalance() < amount) {
			throw new Exception("Account has insufficient funds.");
		}
		
		if(amount <= 0) {
			throw new Exception("Invalid amount.");
		}
		
		sender.setBalance(sender.getBalance() - amount);
		receiver.setBalance(receiver.getBalance() + amount);
		
		accountRepo.save(sender);
		accountRepo.save(receiver);
	}
	
	public void sendMoney(TransactionDto transactionDto) throws Exception {
        Account sender = accountRepo.findById(transactionDto.getSenderId()).orElseThrow(() -> new Exception("Account not found."));
        Account receiver = accountRepo.findById(transactionDto.getReceiverId()).orElseThrow(() -> new Exception("Account not found."));

        if(sender.getBalance() < transactionDto.getAmount()) {
            throw new Exception("Account has insufficient funds.");
        }

        if(transactionDto.getAmount() <= 0) {
            throw new Exception("Invalid amount.");
        }

        sender.setBalance(sender.getBalance() - transactionDto.getAmount());
        receiver.setBalance(receiver.getBalance() + transactionDto.getAmount());

        accountRepo.save(sender);
        accountRepo.save(receiver);
    }
	
	public void requestMoney(String requesterUsername, String giverUsername, int amount) throws Exception {
        Account requester = accountRepo.findByUsername(requesterUsername);
        Account giver = accountRepo.findByUsername(giverUsername);

        if(requester == null || giver == null) {
            throw new Exception("Account not found.");
        }

        if(giver.getBalance() < amount) {
            throw new Exception("Account has insufficient funds.");
        }

        if(amount <= 0) {
            throw new Exception("Invalid amount.");
        }

        giver.setBalance(giver.getBalance() - amount);
        requester.setBalance(requester.getBalance() + amount);

        accountRepo.save(requester);
        accountRepo.save(giver);
    }
	
	public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }
	
	public Account saveUser(Account account){
        return accountRepo.save(account);
    }
	
	public Account findUserById(Long userId){
        return accountRepo.findById(userId).orElse(null);
    }
	
	public Account updateUser(Account account) {
        Account existingUser = accountRepo.findById(account.getId()).orElse(null);
        if(existingUser != null) {
            existingUser.setUsername(account.getUsername());
            existingUser.setEmail(account.getEmail());
            existingUser.setPhone(account.getPhone());
            existingUser.setPassword(account.getPassword());
            existingUser.setBalance(account.getBalance());
            accountRepo.save(existingUser);
        }
        return existingUser;
    }
	
	public Account findUserByUsername(String username) {
        return accountRepo.findByUsername(username);  
    }
}
