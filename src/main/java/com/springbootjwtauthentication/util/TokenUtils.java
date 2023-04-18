package com.springbootjwtauthentication.util;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.springbootjwtauthentication.entity.User;
import com.springbootjwtauthentication.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class TokenUtils {
    private static UserRepository staticUserRepository;

    @Resource
    private UserRepository userRepository;

    @PostConstruct
    public void setUserService() {
        staticUserRepository = userRepository;
    }

    /**
     * Generate token
     *
     * @return
     */
    public static String genToken(String adminId, String sign) {
        return JWT.create().withAudience(adminId) // Save the user id in the token as a payload
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // The token expires after 2 hours
                .sign(Algorithm.HMAC256(sign)); // Use password as the key of the token
    }

    public static String genToken(String adminId, String sign, int days) {
        return JWT.create().withAudience(adminId) // Save the user id in the token as a payload
                .withExpiresAt(DateUtil.offsetDay(new Date(), days)) // The token expires after 2 hours
                .sign(Algorithm.HMAC256(sign)); // Use password as the key of the token
    }

    /**
     * Get the currently logged in user information
     *
     * @return user
     *  /admin?token=xxxx
     */
    public static User getCurrentUser() {
        String token = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token = request.getHeader("token");
            if (StrUtil.isBlank(token)) {  // If there is no header, get it from the parameter
                token = request.getParameter("token");
            }
            if (StrUtil.isBlank(token)) {
                log.error("Failed to get the current login token, token: {}", token);
                return null;
            }
            String userId = JWT.decode(token).getAudience().get(0);
            Optional<User> userOptional =  staticUserRepository.findById(Long.parseLong(userId));
            return userOptional.isPresent() ? userOptional.get() : null;
        } catch (Exception e) {
            log.error("Failed to get the currently logged in administrator information, token={}", token,  e);
            return null;
        }
    }
}

