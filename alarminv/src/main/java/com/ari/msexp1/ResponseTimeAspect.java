package com.ari.msexp1;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ResponseTimeAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Around("@annotation(com.ari.msexp1.mongoDAL.DAL.AlarmDALImpl.*)")
    @Around("execution(* com.ari.msexp1.mongoDAL.DAL.AlarmDALImpl.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage());
        }
        finally {
            long timeTaken = System.currentTimeMillis() - startTime;
            logger.info("Time Taken by {} is {}", joinPoint, timeTaken);
            return result;
        }

    }

}
