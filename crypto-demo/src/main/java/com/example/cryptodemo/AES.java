package com.example.cryptodemo;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AES {

	private static final Logger logger = LogManager.getLogger(AES.class);

	protected String source;
	protected String secret;
	protected String result;
	protected byte[] salt;
	protected byte[] iv;

	protected String doEncode(byte[] source) {
		return Base64.getEncoder().encodeToString(source);
	}

	protected byte[] doDecode(String source) {
		return Base64.getDecoder().decode(source.getBytes(StandardCharsets.UTF_8));
	}

	protected void doEncrypt() {
		try {
			Cipher cipher = createCipher(Cipher.ENCRYPT_MODE, secret);
			this.result = doEncode(cipher.doFinal(source.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	protected void doDecrypt() {
		try {
			Cipher cipher = createCipher(Cipher.DECRYPT_MODE, secret);
			this.result = new String(cipher.doFinal(doDecode(source)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private Cipher createCipher(int mode, String secret) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeyException, InvalidParameterSpecException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec secretKeySpec = createSecretKey(secret);
		cipher.init(mode, secretKeySpec);
		this.iv = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
		return cipher;
	}

	private SecretKeySpec createSecretKey(String secret) {
		try {
			SecureRandom random = new SecureRandom();
			this.salt = new byte[16];
			random.nextBytes(this.salt);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

			PBEKeySpec spec = new PBEKeySpec(secret.toCharArray(), this.salt, 1001, 256);
			SecretKey secretKey = factory.generateSecret(spec);
			SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

			return secretKeySpec;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	public String getSource() {
		return source;
	}

	public String getSecret() {
		return secret;
	}

	public String getResult() {
		return result;
	}

	public byte[] getSalt() {
		return salt;
	}

	public byte[] getIv() {
		return iv;
	}

	public static class AESEncrypt extends AES {

		public AESEncrypt(String source, String secret) {
			this.source = source;
			this.secret = secret;
			doEncrypt();
		}
	}

	public static class AESDecrypt extends AES {

		public AESDecrypt(String source, String secret, byte[] salt, byte[] iv) {
			this.source = source;
			this.secret = secret;
			this.salt = salt;
			this.iv = iv;
			doDecrypt();
		}
	}
}
