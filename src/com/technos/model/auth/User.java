package com.technos.model.auth;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author Felipe
 * 
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "username", length = 40)
	private String username;
	@Column(name = "password", length = 40)
	private String password;
	@Column(name = "enable", columnDefinition = "BOOLEAN")
	private boolean enable;
	@ManyToMany
	@JoinTable(name = "USER_AUTH", joinColumns = @JoinColumn(name = "USER_Username"), inverseJoinColumns = @JoinColumn(name = "AUTH_authority"))
	private List<Authority> authorities;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
}