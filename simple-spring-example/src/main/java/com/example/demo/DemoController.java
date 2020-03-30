package com.example.demo;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	private final ReloadableResourceBundleMessageSource alternativeMessageSource;

	public DemoController(
			@Qualifier("alternativeMessageSource") ReloadableResourceBundleMessageSource alternativeMessageSource) {
		this.alternativeMessageSource = alternativeMessageSource;
	}

	@RequestMapping("/")
	public String home() {
		String englishMessage1 = alternativeMessageSource.getMessage("demo.app.test.1", null, Locale.ENGLISH);
		String germanMessage1 = alternativeMessageSource.getMessage("demo.app.test.1", null, Locale.GERMAN);
		String englishMessage2 = alternativeMessageSource.getMessage("demo.app.test.2", null, Locale.ENGLISH);
		String germanMessage2 = alternativeMessageSource.getMessage("demo.app.test.2", null, Locale.GERMAN);

		return "Hello Docker World<br />" + englishMessage1 + "<br />" + germanMessage1 + "<br />" + englishMessage2 + "<br />" + germanMessage2;
	}

	@RequestMapping("/reload")
	public String reload() throws IOException {
		ClassPathResource englishResource = findResource("alternatives.properties");
		IOUtils.write("demo.app.test.1=reloaded test value 1", new FileOutputStream(englishResource.getFile()), UTF_8);

		ClassPathResource germanResource = findResource("alternatives_de.properties");
		IOUtils.write("demo.app.test.1=reloaded german test value 1", new FileOutputStream(germanResource.getFile()), UTF_8);

		alternativeMessageSource.clearCache();
		return "Reloaded";
	}

	protected ClassPathResource findResource(String configurationFileName) {
		return new ClassPathResource(configurationFileName);
	}
}
