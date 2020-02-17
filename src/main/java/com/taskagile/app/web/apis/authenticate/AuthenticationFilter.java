package com.taskagile.app.web.apis.authenticate;

import com.taskagile.app.utils.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    public AuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/authentications", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        log.debug("Processing login request");

        String requestBody = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        LoginRequest loginRequest = JsonUtils.toObject(requestBody, LoginRequest.class);
        if (loginRequest == null || loginRequest.isInvalid()) {
            throw new InsufficientAuthenticationException("Invalid authentication request");
        }

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password);

        log.info("########");
        log.info(token.toString());

        return this.getAuthenticationManager().authenticate(token);
    }

    static class LoginRequest {
        private String username;
        private String password;

        public boolean isInvalid() {
            return StringUtils.containsWhitespace(username) || StringUtils.containsWhitespace(password);
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}

