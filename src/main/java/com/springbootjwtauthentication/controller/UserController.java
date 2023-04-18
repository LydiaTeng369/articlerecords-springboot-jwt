package com.springbootjwtauthentication.controller;
import com.springbootjwtauthentication.common.Result;
import com.springbootjwtauthentication.entity.User;
import com.springbootjwtauthentication.exception.ServiceException;
import com.springbootjwtauthentication.repository.UserRepository;
import com.springbootjwtauthentication.request.LoginForm;
import com.springbootjwtauthentication.request.SignUpForm;
import com.springbootjwtauthentication.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/fromLogin")
    public String fromLogin() {
        return "login";
    }

    @GetMapping("/fromRegister")
    public String fromRegister() {
        return "register";
    }

    @ResponseBody
    @PostMapping("/login")
    public Result login(@Valid @RequestBody LoginForm loginRequest, HttpServletRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        if (userOptional.isPresent()) {
            String pwd1 = loginRequest.getPassword();
            String pwd2 = userOptional.get().getPassword();
            if (!pwd2.equals(pwd1)) {
                throw new ServiceException("100003", "Wrong password!");
            }
        } else {
            throw new ServiceException("100004", "The account does not exist!");
        }
        User user = userOptional.get();
        HttpSession session = request.getSession();
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());
        String token = TokenUtils.genToken(String.valueOf(user.getId()), user.getPassword());
        session.setAttribute("token", token);
        return Result.success(token);
    }

    @PostMapping("/register")
    public String register(@Valid SignUpForm signUpForm) {
        Optional<User> userOptional = userRepository.findByUsername(signUpForm.getUsername());
        if (userOptional.isPresent()) {
            throw new ServiceException("100001", "This user already exists.");
        }
        if (!signUpForm.getPassword().equals(signUpForm.getConfirmPassword())){
            throw new ServiceException("100002", "Passwords do not match.");
        }
        User user = new User();
        user.setEmail(signUpForm.getEmail());
        user.setUsername(signUpForm.getUsername());
        user.setName(signUpForm.getUsername());
        user.setPassword(signUpForm.getPassword());
        userRepository.save(user);
        return "login";
    }
}
