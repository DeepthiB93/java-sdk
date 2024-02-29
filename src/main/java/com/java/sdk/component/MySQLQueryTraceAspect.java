package com.java.sdk.component;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MySQLQueryTraceAspect {
	
	private Tracer tracer;
	private final OpenTelemetry openTelemetry;
	
	@Value("${spring.application.name}")
	private String applicationName;

	@Autowired
	public MySQLQueryTraceAspect(OpenTelemetry openTelemetry) {
		this.openTelemetry = openTelemetry;
		this.tracer = openTelemetry.getTracer("MySQL_Library");
	}  

    @Before("execution(* com.java.sdk.repository.*.*(..))")
    public void beforeQueryExecution(JoinPoint joinPoint) {
        String queryName = joinPoint.getSignature().toShortString();
        Span span = tracer.spanBuilder(queryName).startSpan();
        span.setAttribute("query", queryName);
        span.end();
    }

    @AfterReturning(pointcut = "execution(* com.java.sdk.repository.*.*(..))", returning = "result")
    public void afterQueryExecution(JoinPoint joinPoint, Object result) {
        Span span = Span.current();
        span.end();
    }
}

