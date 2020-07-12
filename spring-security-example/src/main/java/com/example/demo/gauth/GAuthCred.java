package com.example.demo.gauth;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(
		name="gauth_creds"
)
public class GAuthCred {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	@Column(name="user_name", length=250, nullable=false, unique = true)
	private String userName;
	@Column(name="secret_key", length=250, nullable=false)
	private String secretKey;
	@Column(name="scratch_codes", length=250, nullable=false)
	private String scratchCodes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getScratchCodes() {
		return scratchCodes;
	}

	public List<Integer> getScratchCodesAsList() {
		if (StringUtils.isBlank(scratchCodes)) {
			return new ArrayList<>();
		}
		return Stream.of(scratchCodes.split(":"))
				.map(Integer::valueOf)
				.collect(Collectors.toList());
	}

	public void setScratchCodes(String scratchCodes) {
		this.scratchCodes = scratchCodes;
	}
}
