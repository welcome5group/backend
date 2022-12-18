package fingerorder.webapp.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
// 정확히 이 필터가 하는 역할은 요청으로 들어온 JWT 인증을 하는 역할을 한다.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisTemplate redisTemplate;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		// 요청으로부터 토큰을 복호화 하고
		String token = this.resolveTokenFromRequest(request);

		// 로그아웃된 사용자인지 확인
		if (StringUtils.hasText(token) && isBlocked(token)) {
			throw new RuntimeException("로그아웃된 사용자 입니다.");
		}

		// 들어온 access 토큰이 logout 된 사용자인지 확인
		// 토큰이 존재(즉, 클라이언트로 부터의 요청에 토큰이 있는지 확인하고)하고
		// 토큰의 유효성 검증
		// 후 해당 토큰의 인증 정보를 가져와서 SecurityContext에 전달
		if (StringUtils.hasText(token) && this.jwtTokenProvider.validateToken(token)) {
			// 토큰 인증 정보를 만들어 낸다.
			Authentication auth = this.jwtTokenProvider.getAuthentication(token);
			// SecurityContext 에 전달해서 인증
			SecurityContextHolder.getContext().setAuthentication(auth);
		}


		//이후 필터로 접근해서 걸러줄걸 걸러준다.
		filterChain.doFilter(request,response);
	}

	private boolean isBlocked(String token) {
		if (redisTemplate.opsForValue().get(token) != null) {
			return false;
		}
		return true;
	}

	// 토큰 파싱
	private String resolveTokenFromRequest(HttpServletRequest request) {
		String token = request.getHeader(TOKEN_HEADER);

		if (!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
			return token.substring(TOKEN_PREFIX.length());
		}

		return null;
	}
}
