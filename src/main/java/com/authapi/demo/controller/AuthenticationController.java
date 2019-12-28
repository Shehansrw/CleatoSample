package com.authapi.demo.controller;

import com.authapi.demo.configurations.JwtTokenUtil;
import com.authapi.demo.dao.UserDao;
import com.authapi.demo.model.*;
import com.authapi.demo.service.UserService;
import com.authapi.demo.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
//@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private ValidationUtil validationUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/token/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthToken> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        final User user = userService.findOne(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        return new ApiResponse<>(200, "success", new AuthToken(token, user.getUsername()));
    }
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ApiResponse<AuthToken> signup(@Valid @RequestBody UserDto signupUser, BindingResult result) throws AuthenticationException {

        User user = userService.findOne(signupUser.getUsername());
        if(user != null){
            return new ApiResponse<>(401, "user has already registered to the system", signupUser.getUsername());
        }
        user = userService.save(signupUser);
        final String token = jwtTokenUtil.generateToken(user);
        return new ApiResponse<>(200, "success", new AuthToken(token, user.getUsername()));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResponse<Object> login(@Valid @RequestBody LoginUser loginUser, BindingResult result) throws AuthenticationException {
        FieldError error;
        // custom Validations
        error = validationUtil.userNameValidation(loginUser.getUsername(),"255","2",true,false,"loginUser","username",null);
        if(error != null){
            result.addError(error);
            error = null;
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        User user = userService.findOne(loginUser.getUsername());
        if(user == null){
            error = new FieldError("loginUser", "username", messageSource.getMessage("validation.user.cant", new String[]{"'"+loginUser.getUsername()+"'"}, Locale.getDefault()));
            if(error != null){
                result.addError(error);
            }
        }

        // default came from javax.validations
        if(result.hasErrors()){
            return new ApiResponse<>(400,"bad request" , result.getAllErrors());
        }

        final String token = jwtTokenUtil.generateToken(user);
        return new ApiResponse<>(200, "success", new AuthToken(token, user.getUsername()));
    }
}
