package com.example.demo;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	private final MessageSource messageSource;

	public DemoController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@RequestMapping("/")
	public String home() {
		String englishMessage = messageSource.getMessage("demo.app.test.1", null, Locale.ENGLISH);
		String germanMessage = messageSource.getMessage("demo.app.test.1", null, Locale.GERMAN);

		return "Hello Docker World<br />" + englishMessage + "<br />" + germanMessage;
	}
}
