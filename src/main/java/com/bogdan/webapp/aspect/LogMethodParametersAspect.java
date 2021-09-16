package com.bogdan.webapp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogMethodParametersAspect {

    private Logger logger = LoggerFactory.getLogger(LogMethodParametersAspect.class);

    @Around("@annotation(com.bogdan.webapp.annotation.LogMethodParameters)")
    public Object logMethodParameters(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

        Arrays.stream(signature.getParameterNames())
                .forEach(o -> logger.info("Parameter values: " + o));

        Arrays.stream(proceedingJoinPoint.getArgs())
                .forEach(s -> logger.info("Args values: " + s.toString()));


        return proceedingJoinPoint.proceed();
    }

}
