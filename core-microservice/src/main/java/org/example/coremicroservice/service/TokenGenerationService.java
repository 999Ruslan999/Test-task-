package org.example.coremicroservice.service;

import org.example.coremicroservice.dto.request.AuthRequestDto;
import org.example.coremicroservice.dto.request.UserCredentialRequestDto;
import org.example.coremicroservice.dto.response.JwtTokenResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface TokenGenerationService extends UserDetailsService {

    void addUserCredential(UserCredentialRequestDto userCredentialRequestDto);

    JwtTokenResponseDto buildJwtTokenResponseDto(AuthRequestDto authRequestDto);

}
