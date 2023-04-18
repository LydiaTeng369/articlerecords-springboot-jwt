package com.springbootjwtauthentication.jwt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.springbootjwtauthentication.entity.User;
import com.springbootjwtauthentication.exception.ServiceException;
import com.springbootjwtauthentication.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    private static final String ERROR_CODE_401 = "401";

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        if (StringUtils.isBlank(token)){
            HttpSession session = request.getSession();
            token = (String) session.getAttribute("token");
        }
        // Execute authentication
        if (StringUtils.isBlank(token)) {
            response.sendRedirect(request.getContextPath() + "/api/user/fromLogin");
    /*        request.getRequestDispatcher("/api/user/login").forward(request, response);*/
            throw new ServiceException(ERROR_CODE_401, "You do not have the token. Please login again.");
        }
        // Get the adminId in the token
        User user;
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
            // Query the database according to the userid in the token
            Optional<User> userOptional = userRepository.findById(Long.parseLong(userId));
            user = userOptional.get();
        } catch (Exception e) {
            String errMsg = "Token verification failed, please log in again.";
            log.error(errMsg + ", token=" + token, e);
            response.sendRedirect(request.getContextPath() + "/api/user/fromLogin");
            throw new ServiceException(ERROR_CODE_401, errMsg);
        }
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/api/user/fromLogin");
            throw new ServiceException(ERROR_CODE_401, "User does not exist, please login again.");
        }
        String password = user.getPassword();
        try {
            // User password signature verification token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(password)).build();
            jwtVerifier.verify(token); // Verify token
        } catch (JWTVerificationException e) {
            response.sendRedirect(request.getContextPath() + "/api/user/fromLogin");
            throw new ServiceException(ERROR_CODE_401, "Token verification failed, please login again.");
        }
        return true;
    }
}

