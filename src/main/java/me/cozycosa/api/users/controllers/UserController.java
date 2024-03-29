package me.cozycosa.api.users.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.cozycosa.api.users.DTO.LoginResponse;
import me.cozycosa.api.users.DTO.UserDto;
import me.cozycosa.api.users.helpers.JwtHelper;
import me.cozycosa.api.users.services.TokenBlackList;
import me.cozycosa.api.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import static me.cozycosa.api.users.helpers.JwtHelper.extractTokenFromRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private TokenBlackList tokenBlackList;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping(value = "/signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody UserDto user) {
        return new ResponseEntity<>(service.create(user), HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserDto user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch (BadCredentialsException e) {
            throw e;
        }

        String token = JwtHelper.generateToken(user.getEmail());

        return ResponseEntity.ok(new LoginResponse(user.getEmail(), token));
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        tokenBlackList.addToBlackList(token);

        return ResponseEntity.ok("Déconnexion réussie");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK);
    }
}
