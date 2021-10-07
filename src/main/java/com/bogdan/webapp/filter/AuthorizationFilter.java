package com.bogdan.webapp.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bogdan.webapp.ErrorsEnum;
import com.bogdan.webapp.properties.AuthorizationProperties;
import com.bogdan.webapp.service.CustomRequestBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthorizationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

    @Autowired
    private AuthorizationProperties authorizationProperties;

    @Autowired
    private CustomRequestBean customRequestBean;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String jwtToken = httpServletRequest.getHeader("jwtToken");

        if (StringUtils.isBlank(jwtToken)) {
            logger.error(ErrorsEnum.TOKEN_MISSING.getErrorDescription());
            httpServletResponse.sendError(ErrorsEnum.TOKEN_MISSING.getHttpStatus().value(),
                    ErrorsEnum.TOKEN_MISSING.getErrorDescription());
            return;
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(authorizationProperties.getAlgorithmSecret());
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(authorizationProperties.getIssuer()).build();
            verifier.verify(jwtToken);
            DecodedJWT decodedJwtToken = JWT.decode(jwtToken);
            String userId = decodedJwtToken.getSubject();
            String userName = decodedJwtToken.getClaim("username").asString();
            customRequestBean.setUserId(Integer.parseInt(userId));
            customRequestBean.setUserName(userName);
            System.out.println(userId + StringUtils.SPACE + userName);

        } catch (JWTVerificationException e) {
            logger.error(ErrorsEnum.TOKEN_INVALID.getErrorDescription(), e);
            httpServletResponse.sendError(ErrorsEnum.TOKEN_INVALID.getHttpStatus().value(), e.getMessage());
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return StringUtils.equalsAny(path, "/students/login", "/students/register");
    }
}
