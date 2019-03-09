package com.ari.msexp1.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Place holder for rate limiters - likely move to api gw / lb
 *
 * Types of rate limiters
 * - Request Rate limit - max num requests per time interval - fixed, sliding
 * - Concurrent Rate limit - max num concurrent requests at same time
 * - Priority Based Rate limit - portion of bw reserved for hi-priority
 * - utilization based rate limit - final type to prevent overload - based on priority - critical, posts, gets, tests
 *
 * Notes:
 * - can be done in API Gateway or in conjunction between API Gateway and REST Controller/DB - should use in memory db to maintain state
 *   - db has issues of added latency - can use eventual consistency model
 * - having only a rate limiter per container REST controller can work if the LB uses sticky sessions. It depends on LB model. Not so flexible
 */


@Component
public class RateLimitInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String clientId = request.getHeader("Client-Id");
        // let non-API requests pass
        if (clientId == null) {
            return true;
        }

        // hack - make dynamic when hooked to rate limiter impls
        response.addHeader("X-RateLimit-Limit", String.valueOf("50"));
        response.addHeader("X-RateLimit-Remaining", String.valueOf("50"));
        response.addHeader("X-RateLimit-Reset", String.valueOf("60"));

        return true;
    }


    @PreDestroy
    public void destroy() {
        // loop and finalize all limiters
    }
}