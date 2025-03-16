package com.luannv.order.utils;

import com.luannv.order.models.UserEntity;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtUtils {
	public static String generateToken(UserEntity userEntity, String secretKey) throws JOSEException {
		JWSSigner jwsSigner = new MACSigner(secretKey);
		JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
		JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
						.subject(userEntity.getUsername())
						.jwtID(userEntity.getId())
						.claim("scope", userEntity.getRoleUser().getRoleName())
						.expirationTime(new Date(
										Instant.now().plus(2, ChronoUnit.HOURS).toEpochMilli()
						))
						.build();
		SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
		signedJWT.sign(jwsSigner);

		return signedJWT.serialize();
	}
	public static boolean isValidToken(String token, String secretKey) throws ParseException, JOSEException {
		SignedJWT signedJWT = SignedJWT.parse(token);
		JWSVerifier jwsVerifier = new MACVerifier(secretKey);

		return signedJWT.verify(jwsVerifier) && new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime());
	}
}
