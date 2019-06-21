package com.example.customdependencyresolver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.stereotype.Component;

import com.example.customdependencyresolver.domain.Renderer;

@Component
public class CustomAutowireCandidateResolver extends QualifierAnnotationAutowireCandidateResolver {

	private static final Logger logger = LogManager.getLogger(CustomAutowireCandidateResolver.class);

	@Override
	protected boolean checkGenericTypeMatch(BeanDefinitionHolder bdHolder, DependencyDescriptor descriptor) {
		if (shouldWiredByQualifier(bdHolder)) {
			return true;
		}
		return super.checkGenericTypeMatch(bdHolder, descriptor);
	}

	private boolean shouldWiredByQualifier(BeanDefinitionHolder bdHolder) {
		return shouldWiredByQualifierBasedOnName(bdHolder)
				&& shouldWiredByQualifierBasedOnType(bdHolder);
	}

	private boolean shouldWiredByQualifierBasedOnName(BeanDefinitionHolder bdHolder) {
		return "HtmlRenderer".equalsIgnoreCase(bdHolder.getBeanName());
	}

	private boolean shouldWiredByQualifierBasedOnType(BeanDefinitionHolder bdHolder) {
		try {
			Class<?> beanClass = Class.forName(bdHolder.getBeanDefinition().getBeanClassName());
			logger.info("Resolved {} for {}", beanClass, bdHolder.getBeanName());
			return Renderer.class.isAssignableFrom(beanClass);
		} catch (ClassNotFoundException e) {
			logger.error(e);
		}
		return false;
	}
}
