package com.shop.smart_commerce_api.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.shop.smart_commerce_api.entities.User;
import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtGeneratorService {
    @NonFinal
    @Value("${spring.jwt.signer-key}")
    private String SIGNER_KEY;

    @NonFinal
    @Value("${spring.jwt.access_token_valid_duration}")
    private Long ACCESS_TOKEN_VALID_DURATION;

    public String generateAccessToken(User user) {
        try {
            JWTClaimsSet claimSet = new JWTClaimsSet.Builder()
                    .subject(user.getEmail())
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plus(ACCESS_TOKEN_VALID_DURATION, ChronoUnit.MINUTES)))
                    .jwtID(UUID.randomUUID().toString())
                    .claim("userId", user.getId())
                    .build();

            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS512),
                    new Payload(claimSet.toJSONObject()));

            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

            return jwsObject.serialize();
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }

    public SignedJWT verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

            if (!signedJWT.verify(verifier)) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }

            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            if (claimsSet.getExpirationTime().before(new Date())) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }

            return signedJWT;
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }

}
