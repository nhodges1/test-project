package com.example.demo.models;

import jakarta.persistence.*;

@Entity
public class Transaction {
	@Id
	@GeneratedValue
	private Long Id;
	private int amount;

	// Transaction Getters and Setters
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Account getSender() {
		return sender;
	}

	public void setSender(Account sender) {
		this.sender = sender;
	}

	public Account getReceiver() {
		return receiver;
	}

	public void setReceiver(Account receiver) {
		this.receiver = receiver;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@ManyToOne
	@JoinColumn(name = "sender_id")
	private Account sender;
	
	@ManyToOne
	@JoinColumn(name = "receiver_id")
	private Account receiver;
}
