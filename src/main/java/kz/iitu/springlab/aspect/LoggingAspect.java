package kz.iitu.springlab.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Order(2)
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);


    @Before("kz.iitu.springlab.aspect.PointCuts.serviceOperation()")
    public void before(JoinPoint jp) {
        log.info("[LOG] -> {} args={}",
                jp.getSignature().toShortString(),
                Arrays.toString(jp.getArgs()));
    }

    @AfterReturning(pointcut = "kz.iitu.springlab.aspect.PointCuts.serviceOperation()", returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {
        log.info("[LOG] <- {} returned={}",
                jp.getSignature().toShortString(),
                result);
    }

    @AfterThrowing(pointcut = "kz.iitu.springlab.aspect.PointCuts.serviceOperation()", throwing = "ex")
    public void afterThrowing(JoinPoint jp, Exception ex) {
        log.info("[LOG] <X {} threw {} with message: {}",
                jp.getSignature().toShortString(),
                ex.getClass().getSimpleName(),
                ex.getMessage());
    }


    @Around("kz.iitu.springlab.aspect.PointCuts.serviceOperation()")
    public Object meassure(ProceedingJoinPoint pjp) throws Throwable {
        long started = System.nanoTime();
        try {
            return pjp.proceed();
        } finally {
            long ms = (System.nanoTime() - started) / 1000000;
            String name = pjp.getSignature().toShortString();
            if (ms > 200) {
                log.warn("[TIME] SLOW: {} ms", name, ms);
            } else {
                log.info("[TIME] {} - {} ms", name, ms);
            }
        }
    }
}
