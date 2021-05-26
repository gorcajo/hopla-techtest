package com.example.techtest.security;

import com.example.techtest.exception.UnauthorizedException;
import com.example.techtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Component
public class BearerTokenAuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserRepository userRepository;

    private static final List<String> PUBLIC_PATHS = List.of("/v1/signup", "/v1/signin");

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        var path = request.getRequestURI();
        var authorizationHeader = request.getHeader("Authorization");

        if (PUBLIC_PATHS.contains(path)) {
            return true;
        } else if (Objects.isNull(authorizationHeader)) {
            throw new UnauthorizedException();
        } else if (!authorizationHeader.contains("Bearer ")) {
            throw new UnauthorizedException();
        } else {
            var token = authorizationHeader.split("Bearer ")[1];

            if (userRepository.findByToken(token).isEmpty()) {
                throw new UnauthorizedException();
            }
        }

        return true;
    }
}
