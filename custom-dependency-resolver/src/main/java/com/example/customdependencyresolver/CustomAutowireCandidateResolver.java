package com.example.customdependencyresolver;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.GenericTypeAwareAutowireCandidateResolver;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import com.example.customdependencyresolver.domain.Renderer;

@Component
public class CustomAutowireCandidateResolver extends QualifierAnnotationAutowireCandidateResolver {

	private static final Logger logger = LogManager.getLogger(CustomAutowireCandidateResolver.class);

	@Override
	public boolean isAutowireCandidate(BeanDefinitionHolder bdHolder, DependencyDescriptor descriptor) {
		if (!shouldWiredByQualifier(bdHolder)) {
			return super.isAutowireCandidate(bdHolder, descriptor);
		}

		boolean match = checkQualifiers(bdHolder, descriptor.getAnnotations());
		if (match) {
			MethodParameter methodParam = descriptor.getMethodParameter();
			if (methodParam != null) {
				Method method = methodParam.getMethod();
				if (method == null || void.class == method.getReturnType()) {
					match = checkQualifiers(bdHolder, methodParam.getMethodAnnotations());
				}
			}
		}
		return match;
	}

	@Override
	protected boolean checkGenericTypeMatch(BeanDefinitionHolder bdHolder, DependencyDescriptor descriptor) {
		if (!shouldWiredByQualifier(bdHolder)) {
			return super.checkGenericTypeMatch(bdHolder, descriptor);
		}
		return true;
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
