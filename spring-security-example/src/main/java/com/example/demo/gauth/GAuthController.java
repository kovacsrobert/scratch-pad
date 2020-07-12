package com.example.demo.gauth;

import java.util.Optional;
import java.util.StringTokenizer;

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
	private final GAuthCredRepository gAuthCredRepository;

	@Autowired
	public GAuthController(GoogleAuthenticator googleAuthenticator,
			GAuthCredRepository gAuthCredRepository) {
		this.googleAuthenticator = googleAuthenticator;
		this.gAuthCredRepository = gAuthCredRepository;
	}

	@GetMapping("/authorize/{code}")
	public String authorizeWithDefault(@PathVariable("code") String code) {
		return authorizeWithUserName("default", code);
	}

	@GetMapping("/register/{userName}")
	public String registerUserName(@PathVariable("userName") String userName) {
		try {
			GoogleAuthenticatorKey userSecretKey = googleAuthenticator.createCredentials(userName);
			StringBuilder sb = new StringBuilder();
			sb.append("Successfully registered<br />");
			sb.append("Secret key: ").append(userSecretKey.getKey()).append("<br />");
			sb.append("Scratch codes: ").append(userSecretKey.getScratchCodes()).append("<br />");
			return sb.toString();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GetMapping("/authorize/{userName}/{code}")
	public String authorizeWithUserName(@PathVariable("userName") String userName, @PathVariable("code") String code) {
		try {
			final int verificationCode = Integer.parseInt(code);
			final boolean isCodeValid = googleAuthenticator.authorizeUser(userName, verificationCode);
			return isCodeValid ? "Successful auth for " + userName : "Failed auth for " + userName;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GetMapping("/fallback/{userName}/{scratchCode}")
	public String authorizeWithScratchCode(@PathVariable("userName") String userName, @PathVariable("scratchCode") String scratchCode) {
		try {
			Optional<GAuthCred> gAuthCredOptional = gAuthCredRepository.findByUserName(userName);
			if (gAuthCredOptional.isPresent()) {
				GAuthCred gAuthCred = gAuthCredOptional.get();
				StringTokenizer tokenizer = new StringTokenizer(gAuthCred.getScratchCodes(), ":");
				while (tokenizer.hasMoreTokens()) {
					String token = tokenizer.nextToken();
					if (token.equals(scratchCode)) {
						return "Successful auth for " + userName + "with scratchCode";
					}
				}
			}
			return "Failed auth for " + userName + "with scratchCode";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
