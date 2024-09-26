
package com.hbt.QuanLyGiaoHangBackend.component;

import com.hbt.QuanLyGiaoHangBackend.pojo.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import java.util.Date;
import java.util.StringJoiner;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Component
//@RequiredArgsConstructor
//@Slf4j
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtService {

//    @Value("${jwt.signerKey}")
//   private   String SECRET_KEY ;

    public  String SECRET_KEY = "F34651451$#f$#6f#f#6879vev84$T#$V4t%T5$v#V$";
    public  byte[] SHARED_SECRET_KEY = SECRET_KEY.getBytes();
    public static final int EXPIRE_TIME = 172800000;//token hiệu lực trong 48 giờ

    public String generateTokenLogin(User user) {
        String token = null;
        try {
            JWSSigner signer = new MACSigner(SHARED_SECRET_KEY);
            
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.subject( user.getUsername()).
                    claim("scope", buildScope(user))
                    .claim("customerId", user.getId()+"");

            builder.expirationTime(new Date(System.currentTimeMillis() + EXPIRE_TIME));
            
            JWTClaimsSet claimsSet = builder.build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            
            signedJWT.sign(signer);
            token = signedJWT.serialize();
        } catch (JOSEException e) {
            System.out.println(e.getMessage());
        }
        return token;
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
//                if (!CollectionUtils.isEmpty(role.getPermissions()))
//                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            });

        return stringJoiner.toString();
    }

    public JWTClaimsSet getClaimsFromToken(String token) {
        JWTClaimsSet claims = null;
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SHARED_SECRET_KEY);
            if (signedJWT.verify(verifier)) {
                claims = signedJWT.getJWTClaimsSet();
            }
        } catch (JOSEException | ParseException e) {
            System.err.println(e.getMessage());
        }
        return claims;
    }
    
    private Date getExpirationDateFromToken(String token) {
        JWTClaimsSet claims = getClaimsFromToken(token);
        Date expiration = claims.getExpirationTime();
        return expiration;
    }

    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            JWTClaimsSet claims = getClaimsFromToken(token);
            username = claims.getStringClaim("sub");
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        return username;
    }

    public Long getUserIDFromToken(String token) {
        Long id = null;
        try {
            JWTClaimsSet claims = getClaimsFromToken(token);
            id =Long.parseLong(claims.getStringClaim("customerId")) ;
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        return id;
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateTokenLogin(String token) {
        if (token == null || token.trim().length() == 0) {
            return false;
        }
        String username = getUsernameFromToken(token);

        return !(username == null || username.isEmpty() || isTokenExpired(token));
    }
}