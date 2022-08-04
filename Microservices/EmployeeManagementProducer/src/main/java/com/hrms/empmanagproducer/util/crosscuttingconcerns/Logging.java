package com.hrms.empmanagproducer.util.crosscuttingconcerns;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class Logging {

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        /* This is just a point cut. Implementation is in advice.  */
    }

    /**
     * Pointcut that matches Controllers and Producers in our application
     */
    @Pointcut("within(com.hrms.empmanagproducer.controllers..*) || within(com.hrms.empmanagproducer.services.impl..*)")
    public void applicationPointcut(){
        /*  This is just a point cut. Implementation is in advice.  */
    }


    /**
     * Advice that logs All the methods whether in spring packages or in application packages.
     *
     * @param joinPoint {@link JoinPoint} join point for advice
     * @param ex exception {@link Throwable}
     */
    @AfterThrowing(pointcut = "applicationPointcut() && springBeanPointcut()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), ex.getCause() != null ? ex.getCause() : "NULL");
    }


    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint {@link JoinPoint} join point for advice
     * @return result of type {@link Object}
     * @throws Throwable throws IllegalArgumentException {@link IllegalArgumentException}
     */
    @Around("applicationPointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException ex) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw ex;
        }
    }
}
