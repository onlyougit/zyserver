package com.zyserver.frontservice.pojo;


public class Result{

	private Error Error;

	private String RequestID;

	private String Account;
	
	private Summary Summary;


	public Summary getSummary() {
		return Summary;
	}

	public void setSummary(Summary summary) {
		Summary = summary;
	}

	public Error getError() {
		return Error;
	}

	public void setError(Error error) {
		this.Error = error;
	}

	public String getRequestID() {
		return RequestID;
	}

	public void setRequestID(String requestID) {
		this.RequestID = requestID;
	}

	public String getAccount() {
		return Account;
	}

	public void setAccount(String account) {
		this.Account = account;
	}

	
}
