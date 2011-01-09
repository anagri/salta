package com.salta.core;

public class LoginException extends RuntimeException {
	private String username;
	private String password;
	private String base;

	public LoginException() {
		super();
	}

	public void setUsername(String username) {
		this.username = username;
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
		if (username != null) {
			message.append("Username " + username);
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
