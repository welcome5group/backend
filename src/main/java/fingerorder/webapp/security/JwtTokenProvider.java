package fingerorder.webapp.security;

import fingerorder.webapp.domain.member.dto.TokenDto;
import fingerorder.webapp.domain.member.exception.ExpiredTokenException;
import fingerorder.webapp.domain.member.exception.NoAuthorizedInfoTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
	private static final String BEARER_TYPE = "Bearer";
	// ACCESS 토큰 만료시간
	private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 7 * 24;
	// REFRESH 토큰 만료시간
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;

	@Value("{spring.jwt.secret}")
	private String secretKey;

	public TokenDto genToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		var now = new Date();
		var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

		String accessToken = Jwts.builder()
			.setSubject(authentication.getName())
			.claim("roles",authorities)
			.setIssuedAt(now)
			.setExpiration(expiredDate)
			.signWith(SignatureAlgorithm.HS512,this.secretKey)
			.compact();

		String refreshToken = Jwts.builder()
			.setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
			.signWith(SignatureAlgorithm.HS512,this.secretKey)
			.compact();

		return TokenDto.builder()
			.grantType(BEARER_TYPE)
			.accessToken(accessToken)
			.accessTokenTokenExpirationTime(TOKEN_EXPIRE_TIME)
			.refreshToken(refreshToken)
			.refreshTokenExpirationTime(REFRESH_TOKEN_EXPIRE_TIME)
			.build();
	}

	public TokenDto getToken(Authentication authentication) {
		TokenDto tokenDto = genToken(authentication);

		return tokenDto;
	}

	private Claims parserClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	public boolean validateToken(String token) {
		if(!StringUtils.hasText(token)) return false;

		var claims = this.parserClaims(token);
		boolean checkExpired = !claims.getExpiration().before(new Date());
		if (!checkExpired) {
			throw new ExpiredTokenException();
		}
		return !claims.getExpiration().before(new Date());
	}

	// 토큰에서 email을 가져옴
	public String getEmail(String token) {
		return this.parserClaims(token).getSubject();
	}

	// 토큰에서 Expiration을 가져옴
	public Long getExpiration(String token) {
		Date expiration = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token)
			.getBody().getExpiration();

		Long now = new Date().getTime();
		return (expiration.getTime() - now);
	}

	// jwt 토큰으로 부터 인증 정보를 받아옴
	public Authentication getAuthentication(String jwt) {
		Claims claims = parserClaims(jwt);

		// 권한 정보 존재 확인
		if (claims.get("roles") == null) {
			throw new NoAuthorizedInfoTokenException();
		}

		// 권한 정보들을 가져온다.
		List<GrantedAuthority> authorities =
			Arrays.stream(claims.get("roles").toString().split(","))
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());

		// UserDetails 객체를 만들어 인증 정보 리턴
		UserDetails userDetails = new User(claims.getSubject(),"",authorities);
		return new UsernamePasswordAuthenticationToken(userDetails,"",authorities);
	}
}
