package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

@RestController
@RequestMapping("/gauth")
public class GoogleAuthController {

	private static final String SECRET_KEY = "JB47KDX65ENNHYRCXO5EY2ZGUVR7AJ7U";

	private final GoogleAuthenticator googleAuthenticator;

	private GoogleAuthenticatorKey currentSecret;

	public GoogleAuthController() {
		googleAuthenticator = new GoogleAuthenticator();
		currentSecret = new GoogleAuthenticatorKey.Builder(SECRET_KEY).build();
	}

	@GetMapping("/secret")
	public String getSecret() {
		currentSecret = googleAuthenticator.createCredentials();
		return currentSecret.getKey();
	}

	@GetMapping("/authorize/{code}")
	public String authorize(@PathVariable("code") String code) {
		int verificationCode = Integer.parseInt(code);
		boolean isCodeValid = googleAuthenticator.authorize(currentSecret.getKey(), verificationCode);
		return isCodeValid ? "Successful auth" : "Failed auth";
	}
}
