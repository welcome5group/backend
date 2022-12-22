package fingerorder.webapp.security;

import fingerorder.webapp.domain.member.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;

    private final UserService userService;

    @Value("{spring.jwt.secret}")
    private String secretKey;

    public String genToken(String email, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);

        var now = new Date();
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiredDate)
            .signWith(SignatureAlgorithm.HS512, this.secretKey)
            .compact();
    }

    private Claims parserClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        var claims = this.parserClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    // 토큰에서 email을 가져옴
    public String getEmail(String token) {
        return this.parserClaims(token).getSubject();
    }

    // jwt 토큰으로 부터 인증 정보를 받아옴
    public Authentication getAuthentication(String jwt) {
        UserDetails userDetails = userService.loadUserByUsername(this.getEmail(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails,
            "", userDetails.getAuthorities());
    }
}
