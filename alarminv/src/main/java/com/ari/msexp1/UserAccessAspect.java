package com.ari.msexp1;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class UserAccessAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //What kind of method calls I would intercept
    //execution(* PACKAGE.*.*(..))
    //Weaving & Weaver
    @Before("execution(* com.ari.msexp1.AlarmInventoryRESTController.*(..))")
    public void before(JoinPoint joinPoint){
        //Advice
        logger.info(" Check for user access here");
        logger.info(" Allowed execution for {}", joinPoint);
    }
}




