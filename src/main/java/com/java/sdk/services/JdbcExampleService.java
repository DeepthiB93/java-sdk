package com.java.sdk.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;

@Service
public class JdbcExampleService {
	
	private Tracer tracer;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JdbcExampleService.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcExampleService(JdbcTemplate jdbcTemplate, OpenTelemetry openTelemetry) {
		this.tracer = openTelemetry.getTracer("JdbcExampleService");
        this.jdbcTemplate = jdbcTemplate;
    }

    public int performJdbcOperation() {
    	Span span = tracer.spanBuilder("JDBC-tracer").startSpan();
    	int size = 0;
    	size = jdbcTemplate.getFetchSize();
    	LOGGER.info("Jdbc Tests");
    	span.end();
    	return size;
    }
    
    
}