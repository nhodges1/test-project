package com.example.demp.dto;

public class TransactionDto {
	private Long senderId;
	private Long receiverId;
	private int amount;
	
	public Long getSenderId() {
		return senderId;
	}
	
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	
	public Long getReceiverId() {
		return receiverId;
	}
	
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
