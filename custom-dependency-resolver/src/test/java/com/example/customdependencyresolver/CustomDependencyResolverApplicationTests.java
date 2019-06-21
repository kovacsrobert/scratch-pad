package com.example.customdependencyresolver;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.customdependencyresolver.domain.WidgetFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomDependencyResolverApplicationTests {

	@Autowired
	private WidgetFactory widgetFactory;

	@Test
	public void contextLoads() {
		Assert.assertNotNull(widgetFactory);
	}

}
