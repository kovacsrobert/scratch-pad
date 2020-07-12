package com.example.demo.gauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

@RestController
@RequestMapping("/gauth")
public class GAuthController {

	private final GoogleAuthenticator googleAuthenticator;

	@Autowired
	public GAuthController(GoogleAuthenticator googleAuthenticator) {
		this.googleAuthenticator = googleAuthenticator;
	}

	@GetMapping("/authorize/{code}")
	public String authorizeWithDefault(@PathVariable("code") String code) {
		return authorizeWithUserName("default", code);
	}

	@GetMapping("/register/{userName}")
	public String registerUserName(@PathVariable("userName") String userName) {
		try {
			GoogleAuthenticatorKey userSecretKey = googleAuthenticator.createCredentials(userName);
			return userSecretKey.getKey();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GetMapping("/authorize/{userName}/{code}")
	public String authorizeWithUserName(@PathVariable("userName") String userName, @PathVariable("code") String code) {
		try {
			final int verificationCode = Integer.parseInt(code);
			final boolean isCodeValid = googleAuthenticator.authorizeUser(userName, verificationCode);
			return isCodeValid ? "Successful auth with " + userName : "Failed auth with " + userName;
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
