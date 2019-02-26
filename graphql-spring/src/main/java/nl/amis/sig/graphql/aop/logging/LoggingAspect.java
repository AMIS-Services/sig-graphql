package nl.amis.sig.graphql.aop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * This aspect is applied using the
 * nl.amis.sig.graphql.configLoggingAspectConfiguration. It wraps around all
 * methods that satisfy the pointcuts and adds debug logging to provide insight
 * in the values of the objects entering and exiting the methods.
 */
@Aspect
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(@org.springframework.stereotype.Repository *)"
            + " || within(@org.springframework.stereotype.Service *)"
            + " || within(@org.springframework.stereotype.Component *)"
            + " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
    }

    @Pointcut("within(nl.amis.sig.graphql.repository..*)" + " || within(nl.amis.sig.graphql.service..*)"
            + " || within(nl.amis.sig.graphql.web..*)")
    public void applicationPackagePointcut() {
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        try {
            Object result = joinPoint.proceed();
            log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

            throw e;
        }
    }
}
