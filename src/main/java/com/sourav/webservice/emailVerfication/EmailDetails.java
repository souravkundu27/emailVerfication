package com.sourav.webservice.emailVerfication;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmailDetails implements Serializable {
    
	@Id
	private String addr;
	private int code;
	private long creationTime;
	private int unsuccessfulVerification;

	public EmailDetails() {
	}

	public EmailDetails(String addr) {
		this.addr = addr;
		this.creationTime = Instant.now().toEpochMilli();
	}

	public EmailDetails(String addr, int code) {
		super();
		this.addr = addr;
		this.code = code;
		this.creationTime = Instant.now().toEpochMilli();
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public int getNoOfTimesVerified() {
		return unsuccessfulVerification;
	}

	public int getUnsuccessfulVerification() {
		return unsuccessfulVerification;
	}

	public void setUnsuccessfulVerification(int unsuccessfulVerification) {
		this.unsuccessfulVerification = unsuccessfulVerification;
	}

	@Override
	public String toString() {
		return "EmailDetails [addr=" + addr + ", code=" + code + ", creationTime=" + creationTime
				+ ", noOfTimesVerified=" + unsuccessfulVerification + "]";
	}
}
