package com.salta.core;

public class LoginException extends RuntimeException {
	private String email;
	private String password;
	private String base;

	public LoginException() {
		super();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setBase(String base) {
		this.base = base;
	}

	@Override
	public String getMessage() {
		StringBuilder message = new StringBuilder();
		if (email != null) {
			message.append("Email " + email);
		}
		if (password != null) {
			message.append("Password " + password);
		}
		if (base != null) {
			message.append(base);
		}
		return message.toString();
	}
}
