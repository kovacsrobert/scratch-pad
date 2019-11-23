package com.example.cryptodemo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CryptoDemoApplication implements CommandLineRunner {

	private static final Logger logger = LogManager.getLogger(CryptoDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CryptoDemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		logger.info("----- App started -----");

		final String secret = "ssshhhhhhhhhhh!!!!";

		String source = "howtodoinjava.com";
		logger.info("originalString: {}",  source);
		logger.info("-----");

		AES.AESEncrypt aesEncrypt = new AES.AESEncrypt(source, secret);
		logger.info("aesEncrypt.result: {}", aesEncrypt.getResult());
		logger.info("aesEncrypt.salt: {}", aesEncrypt.getSalt());
		logger.info("aesEncrypt.iv: {}", aesEncrypt.getIv());
		logger.info("-----");

		AES.AESDecrypt aesDecrypt = new AES.AESDecrypt(source, secret, aesEncrypt.getSalt(), aesEncrypt.getIv());
		logger.info("aesDecrypt.result: {}", aesDecrypt.getResult());
		logger.info("aesDecrypt.salt: {}", aesDecrypt.getSalt());
		logger.info("aesDecrypt.iv: {}", aesDecrypt.getIv());

		logger.info("----- App ended -----");
	}
}
