package com.example.customdependencyresolver;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class CustomAutowireCandidateResolverConfigurer implements BeanFactoryPostProcessor {

	private AutowireCandidateResolver autowireCandidateResolver;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
		defaultListableBeanFactory.setAutowireCandidateResolver(autowireCandidateResolver);
	}

	public void setAutowireCandidateResolver(AutowireCandidateResolver autowireCandidateResolver) {
		this.autowireCandidateResolver = autowireCandidateResolver;
	}
}
