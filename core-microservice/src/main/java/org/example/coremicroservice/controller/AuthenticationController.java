package org.example.coremicroservice.controller;

import org.example.coremicroservice.dto.request.AuthRequestDto;
import org.example.coremicroservice.dto.request.UserCredentialRequestDto;
import org.example.coremicroservice.dto.response.JwtTokenResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthenticationController {

    @PostMapping("login")
    ResponseEntity<JwtTokenResponseDto> login(@RequestBody AuthRequestDto authRequestDto);

    @PostMapping("/new")
    ResponseEntity<HttpStatus> addUserCredential(@RequestBody UserCredentialRequestDto userCredentialRequestDto);

}
