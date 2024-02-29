package com.java.sdk.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;

/**
 * 
 * Aspect captures traces before and after methods annotated
 * with @Transactional, which is commonly used with Hibernate operations.
 * 
 */

@Aspect
@Component
public class HibernateAspect {
	private Tracer tracer;
	private final OpenTelemetry openTelemetry;
	
	@Value("${spring.application.name}")
	private String applicationName;

	@Autowired
	public HibernateAspect(OpenTelemetry openTelemetry) {
		this.openTelemetry = openTelemetry;
	}

	@Before("@annotation(org.springframework.transaction.annotation.Transactional)")
	public void beforeTransactionalMethod(JoinPoint joinPoint) {
		this.tracer = openTelemetry.getTracer("Hibernate-Library");
		String queryName = joinPoint.getSignature().toShortString();
        Span span = tracer.spanBuilder(queryName).startSpan();
        span.setAttribute("query", queryName);
		span.end();
	}

	@After("@annotation(org.springframework.transaction.annotation.Transactional)")
	public void afterTransactionalMethod() {
		Span.current().end();
	}
}
