package com.example.demo.gauth;

import java.util.Optional;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

@RestController
@RequestMapping("/gauth")
public class GAuthController {

	private final GoogleAuthenticator googleAuthenticator;
	private final GAuthCredRepository gAuthCredRepository;
	private final RestTemplate restTemplate;
	private final GoogleAuthenticatorConfig googleAuthenticatorConfig;

	@Autowired
	public GAuthController(
			GoogleAuthenticator googleAuthenticator,
			GAuthCredRepository gAuthCredRepository,
			RestTemplate restTemplate,
			GoogleAuthenticatorConfig googleAuthenticatorConfig) {
		this.googleAuthenticator = googleAuthenticator;
		this.gAuthCredRepository = gAuthCredRepository;
		this.restTemplate = restTemplate;
		this.googleAuthenticatorConfig = googleAuthenticatorConfig;
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
				if (gAuthCred.getScratchCodesAsList().contains(Integer.valueOf(scratchCode))) {
					return "Successful auth for " + userName + "with scratchCode";
				}
			}
			return "Failed auth for " + userName + "with scratchCode";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GetMapping("/qr/{userName}")
	public String generateQrCode(HttpServletResponse response, @PathVariable("userName") String userName) {
		try {
			Optional<GAuthCred> gAuthCredOptional = gAuthCredRepository.findByUserName(userName);
			if (gAuthCredOptional.isPresent()) {
				GAuthCred gAuthCred = gAuthCredOptional.get();
				GoogleAuthenticatorKey credentials = new GoogleAuthenticatorKey.Builder(gAuthCred.getSecretKey())
						.setConfig(googleAuthenticatorConfig)
						.setVerificationCode(googleAuthenticator.getTotpPassword(gAuthCred.getSecretKey()))
						.setScratchCodes(gAuthCred.getScratchCodesAsList())
						.build();
				String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthURL("test", "test", credentials);
				byte[] imageBytes = restTemplate.getForObject(otpAuthURL, byte[].class);
				response.setHeader("Content-disposition", "attachment; filename=gauth_qr.png");
				response.setContentLength(imageBytes.length);
				IOUtils.write(imageBytes, response.getOutputStream());
			}
			return "Not found user for " + userName;
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
