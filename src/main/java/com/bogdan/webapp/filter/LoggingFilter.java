package com.bogdan.webapp.filter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class LoggingFilter extends OncePerRequestFilter {

	private final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

	private static String getDurationInSeconds(long duration) {
		NumberFormat numberFormat = new DecimalFormat("#0.00");
		return numberFormat.format(duration / 1000d);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {

		long start = System.currentTimeMillis();

		logger.info("*** Starting <" + httpServletRequest.getMethod() + StringUtils.SPACE
				+ httpServletRequest.getRequestURI() + "> at date: " + new Date());

		filterChain.doFilter(httpServletRequest, httpServletResponse);

		String executionTime = getDurationInSeconds(System.currentTimeMillis() - start);

		logger.info("*** Finished <" + httpServletRequest.getMethod() + StringUtils.SPACE
				+ httpServletRequest.getRequestURI() + "> at date: " + new Date() + ". Execution Time: " + executionTime
				+ " seconds");

	}
}
