package org.example.coremicroservice.controller.impl;

import lombok.RequiredArgsConstructor;
import org.example.coremicroservice.controller.AuthenticationController;
import org.example.coremicroservice.data.model.UserCredential;
import org.example.coremicroservice.dto.request.AuthRequestDto;
import org.example.coremicroservice.dto.request.UserCredentialRequestDto;
import org.example.coremicroservice.dto.response.JwtTokenResponseDto;
import org.example.coremicroservice.service.TokenGenerationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {

    private final TokenGenerationService tokenGenerationService;

    @Override
    public ResponseEntity<JwtTokenResponseDto> login(AuthRequestDto authRequestDto) {
        JwtTokenResponseDto jwtTokenResponseDto = tokenGenerationService.buildJwtTokenResponseDto(authRequestDto);
        return new ResponseEntity<>(jwtTokenResponseDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> addUserCredential(UserCredentialRequestDto userCredentialRequestDto) {
        tokenGenerationService.addUserCredential(userCredentialRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
