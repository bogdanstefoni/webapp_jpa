package com.bogdan.webapp.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bogdan.webapp.ErrorsEnum;
import com.bogdan.webapp.exception.CustomException;
import com.bogdan.webapp.exception.RestResponse;
import com.bogdan.webapp.properties.AuthorizationProperties;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private static final String ERROR_CODE = "errorCode";
    private static final String ERROR_DESCRIPTION = "errorDescription";

    @Autowired
    private static AuthorizationProperties authorizationProperties;

    private Logger logger = LoggerFactory.getLogger(LoggingFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                              FilterChain filterChain) throws ServletException, IOException {



        //de inlocuit
        //avoid methods
        final String jwtToken = httpServletRequest.getHeader("host");



//
        if(StringUtils.isBlank(jwtToken)) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(ERROR_CODE,ErrorsEnum.TOKEN_MISSING.getErrorCode());
            jsonObject.put(ERROR_DESCRIPTION, ErrorsEnum.TOKEN_MISSING.getErrorDescription());

            httpServletResponse.setStatus(ErrorsEnum.TOKEN_MISSING.getHttpStatus().value());
            httpServletResponse.getWriter().write(String.valueOf(RestResponse.createResponse(jsonObject)));
            httpServletResponse.getWriter().flush();

            return;
        }
        if (!"host".equals(jwtToken)) {
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized request");

            return;
        }

        try {

            //Le aduc din proprietati
            Algorithm algorithm = Algorithm.HMAC256(authorizationProperties.getAlgorithmSecret());
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(authorizationProperties.getIssuer()).build();
            verifier.verify(jwtToken);
            DecodedJWT decodedJwtToken = JWT.decode(jwtToken);
            String userId = decodedJwtToken.getSubject();
            String userName = decodedJwtToken.getClaim("username").asString();

            System.out.println(userId + StringUtils.SPACE + userName);

        }catch (JWTVerificationException e) {

            JSONObject jsonObject = new JSONObject();
            //token is invalid creeaza
            jsonObject.put(ERROR_CODE,ErrorsEnum.TOKEN_INVALID.getErrorCode());
            jsonObject.put(ERROR_DESCRIPTION, ErrorsEnum.TOKEN_INVALID.getErrorDescription());

            httpServletResponse.setStatus(ErrorsEnum.TOKEN_INVALID.getHttpStatus().value());
            httpServletResponse.getWriter().write(String.valueOf(RestResponse.createResponse(jsonObject)));
            httpServletResponse.getWriter().flush();

            return;
        }
//
//
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
