package com.core.core.utility;

import com.core.core.constant.Constants;
import com.core.core.exception.error.ErrorCode;
import com.core.core.exception.type.CommonException;
import com.core.core.exception.type.HttpSecurityException;
import com.core.security.application.dto.response.DefaultJsonWebTokenDto;
import com.core.security.domain.type.ESecurityRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JsonWebTokenUtil implements InitializingBean {
    @Value("${json-web-token.secret-key}")
    private String secretKey;

    @Value("${json-web-token.access-token-expire-period}")
    private Long accessTokenExpirePeriod;

    @Getter
    @Value("${json-web-token.refresh-token-expire-period}")
    private Long refreshTokenExpirePeriod;

    private Key key;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public DefaultJsonWebTokenDto generateDefaultJsonWebTokens(UUID id, ESecurityRole role) {
        return new DefaultJsonWebTokenDto(
                generateToken(id.toString(), role, accessTokenExpirePeriod),
                generateToken(id.toString(), null, refreshTokenExpirePeriod)
        );
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException e) {
            throw new HttpSecurityException(e.getMessage(), ErrorCode.TOKEN_MALFORMED_ERROR);
        } catch (IllegalArgumentException e) {
            throw new HttpSecurityException(e.getMessage(), ErrorCode.TOKEN_TYPE_ERROR);
        } catch (ExpiredJwtException e) {
            throw new HttpSecurityException(e.getMessage(), ErrorCode.EXPIRED_TOKEN_ERROR);
        } catch (UnsupportedJwtException e) {
            throw new HttpSecurityException(e.getMessage(), ErrorCode.TOKEN_UNSUPPORTED_ERROR);
        } catch (JwtException e) {
            throw new HttpSecurityException(e.getMessage(), ErrorCode.TOKEN_UNKNOWN_ERROR);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private String generateToken(String identifier, ESecurityRole role, Long expirePeriod) {
        Claims claims = Jwts.claims();

        claims.put(Constants.USER_ID_CLAIM_NAME, identifier);

        if (role != null)
            claims.put(Constants.USER_ROLE_CLAIM_NAME, role);

        return Jwts.builder()
                .setHeaderParam(Header.JWT_TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirePeriod))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}
