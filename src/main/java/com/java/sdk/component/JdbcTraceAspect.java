package com.java.sdk.component;

import java.sql.Connection;

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
import io.opentelemetry.context.Scope;

@Aspect
@Component
public class JdbcTraceAspect {

	private Tracer tracer;
	private final OpenTelemetry openTelemetry;
	
	@Value("${spring.application.name}")
	private String applicationName;

	@Autowired
	public JdbcTraceAspect(OpenTelemetry openTelemetry) {
		this.openTelemetry = openTelemetry;
		this.tracer = openTelemetry.getTracer(applicationName);
	}

    @Before("execution(* org.springframework.jdbc.core.JdbcTemplate.*(..))")
    public void beforeJdbcTemplateMethod(JoinPoint joinPoint) {
        String queryName = joinPoint.getSignature().toShortString();
        Span span = tracer.spanBuilder(queryName).startSpan();
        span.setAttribute("query", queryName);
        span.end();
    }

    @After("execution(* org.springframework.jdbc.core.JdbcTemplate.*(..))")
    public void afterJdbcTemplateMethod() {
        Span.current().end();
    }
}
