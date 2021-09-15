package com.bogdan.webapp.filter;

import com.bogdan.webapp.properties.AdminProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Autowired
    private AdminProperties adminProperties;


    private static String getDurationInSeconds(long duration) {
        NumberFormat numberFormat = new DecimalFormat("#0.00");
        return numberFormat.format(duration / 1000d);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        long start = System.currentTimeMillis();


        logger.info("Starting..." + "JSON Body: " + httpServletRequest.getReader().lines().collect(Collectors.joining()) +
                " " +
                httpServletRequest.getMethod() + " " +
                httpServletRequest.getRequestURI() +
                " at date: " + new Date());


        filterChain.doFilter(httpServletRequest, httpServletResponse);

        String executionTime = getDurationInSeconds(System.currentTimeMillis() - start);

        logger.info("Finished..." + httpServletRequest.getMethod() +
                " " + httpServletRequest.getRequestURI() +
                " at date: " + new Date() + " Execution Time: " + executionTime + " seconds");


        logger.info("Admin name: " + adminProperties.getUser());
        logger.info("Admin password: " + adminProperties.getPassword());

    }
}
