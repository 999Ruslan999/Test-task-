package org.example.coremicroservice.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.coremicroservice.data.event.EmailEvent;
import org.example.coremicroservice.data.event.RegistrationChangeEvent;
import org.example.coremicroservice.data.model.RefreshToken;
import org.example.coremicroservice.data.model.UserCredential;
import org.example.coremicroservice.data.repository.RefreshTokenRepository;
import org.example.coremicroservice.data.repository.UserCredentialRepository;
import org.example.coremicroservice.dto.request.AuthRequestDto;
import org.example.coremicroservice.dto.request.UserCredentialRequestDto;
import org.example.coremicroservice.dto.response.JwtTokenResponseDto;
import org.example.coremicroservice.exception.UserCredentialNotFoundException;
import org.example.coremicroservice.exception.constant.ErrorMessage;
import org.example.coremicroservice.service.TokenGenerationService;
import org.example.coremicroservice.service.converter.UserCredentialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TokenGenerationServiceImpl implements TokenGenerationService {

    private final UserCredentialRepository userCredentialRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final KafkaTemplate<String, EmailEvent> kafkaTemplate;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final UserCredentialMapper mapper;

    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-life-time}")
    private Duration accessTokenLifeTime;

    @Value("${jwt.refresh-life-time}")
    @Setter
    private Duration refreshTokenLifeTime;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userCredentialRepository.findByUsername(username)
                .orElseThrow(() -> new UserCredentialNotFoundException(ErrorMessage.USER_CREDENTIAL_NOT_FOUND.getMessage()));
    }

    @Override
    public void addUserCredential(UserCredentialRequestDto userCredentialRequestDto) {
        UserCredential userCredential = mapper.toEntity(userCredentialRequestDto);
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredentialRepository.save(userCredential);

        String mail = userCredential.getMail();

        EmailEvent event = new EmailEvent(mail);


        CompletableFuture<SendResult<String, EmailEvent>> future = kafkaTemplate.send("registration-new-events-topic",
                userCredentialRequestDto.getClientId().toString(),
                event);

        future.whenComplete((result, exception) -> {
           if(exception != null) {
               LOGGER.error("Не удалось отправить сообщение: {}", exception.getMessage());
           } else {
               LOGGER.info("Сообщение успешно отправлено: {}", result.getRecordMetadata());
           }
        });

    }

    @Override
    public JwtTokenResponseDto buildJwtTokenResponseDto(AuthRequestDto authRequestDto) {

        UserCredential userCredential = findUserCredentialByUsername(authRequestDto.getUsername());
        if (!passwordEncoder.matches(authRequestDto.getPassword(), (userCredential.getPassword()))) {
            throw new UserCredentialNotFoundException(ErrorMessage.INCORRECT_DATA.getMessage());
        }

        return JwtTokenResponseDto.builder()
                .accessToken(generateAccessToken(userCredential))
                .refreshToken(createRefreshToken(userCredential).getToken())
                .build();

    }

    public UserCredential findUserCredentialByUsername(String username) {
        return userCredentialRepository.findByUsername(username)
                .orElseThrow(() -> new UserCredentialNotFoundException(ErrorMessage.INCORRECT_DATA.getMessage()));
    }
    private String generateAccessToken(UserCredential userCredential) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userCredential.getRoles());
        return createAccessToken(claims, userCredential);
    }

    private String createAccessToken (Map < String, Object > claims, UserCredential userCredential){
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + accessTokenLifeTime.toMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userCredential.getClientId().toString())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(getSignKey(secret), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key getSignKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Transactional
    public RefreshToken createRefreshToken(UserCredential userCredential) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userCredential(userCredential)
                .token(UUID.randomUUID().toString())
                .expires(Instant.now().plusMillis(refreshTokenLifeTime.toMillis()))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

}