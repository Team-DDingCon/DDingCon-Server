package com.pocalink.ddingcon.global.interceptor;

import com.pocalink.ddingcon.global.error.CustomException;
import com.pocalink.ddingcon.global.error.ErrorCode;
import com.pocalink.ddingcon.global.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = jwtTokenProvider.extractToken(request);
        if(token==null) {
            throw new CustomException(ErrorCode.NO_AUTH_TOKEN);
        }
        if(!jwtTokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }
        return true;
    }
}
