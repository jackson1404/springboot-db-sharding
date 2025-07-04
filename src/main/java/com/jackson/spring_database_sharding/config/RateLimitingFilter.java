/***************************************************************
 * Author       :	 
 * Created Date :	
 * Version      : 	
 * History  :	
 * *************************************************************/
package com.jackson.spring_database_sharding.config;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * RateLimitingFilter Class.
 * <p>
 * </p>
 *
 * @author
 */

@Component
public class RateLimitingFilter implements Filter {

    private final Map<String, Bucket> ipBucketMap = new ConcurrentHashMap<>();

    private Bucket createBucket(){
        Bandwidth limit = Bandwidth.builder()
                .capacity(5)
                .refillIntervally(5, Duration.ofMinutes(1))
                .build();

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestUrl = request.getRequestURI();

        if(requestUrl.contains("/api/v1/customers/createCustomer")){

            String ipAddress = request.getRemoteAddr();
            Bucket bucket = ipBucketMap.computeIfAbsent(ipAddress, ip -> createBucket());

            if (bucket.tryConsume(1)) {
                System.out.println("The remaining bucket " + bucket.getAvailableTokens());
                chain.doFilter(request, response); // Allowed
            } else {
                response.setStatus(429);
                response.getWriter().write("Rate limit exceeded. Please try again later.");
            }
        } else {
            chain.doFilter(request, response);
        }

    }

}
