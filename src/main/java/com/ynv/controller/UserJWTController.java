package com.ynv.controller;


import com.ynv.domain.User;
import com.ynv.dto.request.LoginRequest;
import com.ynv.dto.request.RegisterRequest;
import com.ynv.dto.response.LoginResponse;
import com.ynv.dto.response.ResponseMessage;
import com.ynv.dto.response.YNVResponse;
import com.ynv.security.jwt.JWTTokenProvider;
import com.ynv.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserJWTController  {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;


    @PostMapping("/register")
    public ResponseEntity<YNVResponse> register(   @Valid @RequestBody RegisterRequest registerRequest
            )  {
        userService.register(registerRequest);

        YNVResponse response = new YNVResponse();
        response.setMessage(ResponseMessage.REGISTER_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest  ) {

        UsernamePasswordAuthenticationToken authToken = new
                UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());


        Authentication auth = authenticationManager.authenticate(authToken);

        System.out.println("auth = " + auth);

        String jwtToken = jwtTokenProvider.generateJWTToken(auth);

        SecurityContextHolder.getContext().setAuthentication(auth);


        User user = userService.getOneUserByUsername(loginRequest.getUsername());

        LoginResponse response= new LoginResponse();
        response.setAccessToken(jwtToken);
        response.setUserId(user.getId());
        response.setMessage(user.getUsername() + "take your token");

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }





}
