package com.borgestech.securityrest.response;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SigninResponse {

	private String username;
	private Set<String> roles;

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("token_type")
	private String tokenType = "Bearer";

	public SigninResponse() {

	}

	public SigninResponse(String username, Set<String> roles, String accessToken) {
		this.username = username;
		this.roles = roles;
		this.accessToken = accessToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

}
