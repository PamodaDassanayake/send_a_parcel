package com.api.sendaparcel.api.controller;

import com.api.sendaparcel.api.dto.UserDTO;
import com.api.sendaparcel.api.security.AppConstant;
import com.api.sendaparcel.api.security.TokenUtils;
import com.api.sendaparcel.api.security.model.AuthenticationRequest;
import com.api.sendaparcel.api.security.model.AuthenticationResponse;
import com.api.sendaparcel.api.security.model.SpringSecurityUser;
import com.api.sendaparcel.api.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(name = "auth_controller", value = "/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest)
            throws AuthenticationException {

        // Perform the authentication
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-authentication so we can generate token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = this.tokenUtils.generateToken(userDetails);

        // get user
        UserDTO user = userService.getUser(authenticationRequest.getUsername());

        // Return the token
        return ResponseEntity.ok(new AuthenticationResponse(userDetails.getUsername(), user.getUserId(),
                user.getUserType(), token));
    }

    @GetMapping(value = "/refresh")
    public ResponseEntity<?> authenticationRequest(HttpServletRequest request) {
        String token = request.getHeader(AppConstant.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(token);
        SpringSecurityUser user = (SpringSecurityUser) this.userDetailsService.loadUserByUsername(username);
        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
            String refreshedToken = this.tokenUtils.refreshToken(token);
            UserDTO user1 = userService.getUser(username);
            return ResponseEntity.ok(new AuthenticationResponse(username,user1.getUserId(),user1.getUserType()
                    ,refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@NotNull @RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return new ResponseEntity<String>("Your account has been created successfully!",
                HttpStatus.CREATED);
    }
}
