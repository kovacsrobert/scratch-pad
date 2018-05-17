package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class MeTestExecutionListener implements TestExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeTestExecutionListener.class);

    public void beforeTestMethod(TestContext testContext) throws Exception {
        LOGGER.info("beforeTestMethod");
    }
}
