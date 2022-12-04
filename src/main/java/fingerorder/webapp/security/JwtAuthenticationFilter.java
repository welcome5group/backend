package fingerorder.webapp.security;

import fingerorder.webapp.config.SecurityConfig;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	private final JwtTokenProvider jwtTokenProvider;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = this.resolveTokenFromRequest(request);

		// token이 있고, 토큰의 유효성 검증 후 해당 토큰의 인증 정보를 가져와서 SecurityContext에 전달
		if (StringUtils.hasText(token) && this.jwtTokenProvider.validateToken(token)) {
			Authentication auth = this.jwtTokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		//이후 필터로 접근
		filterChain.doFilter(request,response);
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
