package com.revs.jwtsecurity.config;

import com.revs.jwtsecurity.token.Token;
import com.revs.jwtsecurity.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutHandlerImpl implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = this.tokenRepository.findByToken(jwt);
        if (storedToken.isPresent()) {
            Token token = storedToken.get();
            token.setRevoked(true);
            token.setExpired(true);
            this.tokenRepository.save(token);
        }
    }
}
