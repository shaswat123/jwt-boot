package com.jwt.controller;


import com.jwt.model.AuthenticationRequest;
import com.jwt.model.AuthenticationResponse;
import com.jwt.service.CustomUserDetailsService;
import com.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/initJwt")
    public String initJwt(){

        return "Hello Jwt";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{


        try{
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),
                            authenticationRequest.getPassword()));

        }catch(BadCredentialsException e){

            throw new Exception("Incorrect username and password",e);
        }
       ;

        return ResponseEntity.ok(new AuthenticationResponse(jwtUtil.
                                                       generateToken(userDetailsService.
                                                       loadUserByUsername(authenticationRequest.getUserName()))));

    }


}
