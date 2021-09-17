package com.bogdan.webapp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.NumberFormat;

@Aspect
@Component
public class LogExecutionTimeAspect {

    private Logger logger = LoggerFactory.getLogger(LogExecutionTimeAspect.class);

    private static String getDurationInSeconds(long duration) {
        NumberFormat numberFormat = new DecimalFormat("#0.00");
        return numberFormat.format(duration / 1000d);
    }

    @Around("@annotation(com.bogdan.webapp.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        String executionTime = getDurationInSeconds(System.currentTimeMillis() - start);

        logger.info("Execution time for method: " + proceedingJoinPoint.getSignature().getDeclaringTypeName() + "." + proceedingJoinPoint.getSignature().getName() +
                ": " + executionTime);

        return proceed;

    }

}
