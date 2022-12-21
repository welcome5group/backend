package fingerorder.webapp.domain.member.controller;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

import fingerorder.webapp.domain.member.dto.MemberDto;
import fingerorder.webapp.domain.member.dto.SignInDto;
import fingerorder.webapp.domain.member.dto.SignOutDto;
import fingerorder.webapp.domain.member.dto.SignUpDto;
import fingerorder.webapp.domain.member.dto.TokenDto;
import fingerorder.webapp.domain.member.dto.MemberEditDto;
import fingerorder.webapp.domain.member.dto.MemberInfoDto;
import fingerorder.webapp.domain.member.dto.MemberPasswordResetDto;
import fingerorder.webapp.domain.member.dto.TokenResponseDto;
import fingerorder.webapp.domain.member.service.MailService;
import fingerorder.webapp.domain.member.service.UserService;
import fingerorder.webapp.security.JwtTokenProvider;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.startup.UserConfig;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final MailService mailService;
	private final UserService userService;

	@PostMapping("/api/auth/sign-up")
	public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpParam) {
		MemberDto unAuthMember = this.userService.signUp(signUpParam);
		this.mailService.sendUserAuthMail(unAuthMember);
		return ResponseEntity.ok(unAuthMember);
	}

	@PostMapping("/api/auth/sign-up/submit")
	public ResponseEntity<?> signUpSubmit(@RequestParam String uuid) {
		MemberDto result = this.userService.signUpSubmit(uuid);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/api/auth/sign-in")
	public ResponseEntity<?> signIn(@RequestBody SignInDto signInDto, HttpServletResponse response) {
		TokenDto tokenDto = this.userService.signIn(signInDto);

		Cookie cookie = new Cookie("refresh_token",tokenDto.getRefreshToken());

		cookie.setMaxAge(tokenDto.getAccessTokenTokenExpirationTime().intValue()/1000);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);

		response.addCookie(cookie);
		TokenResponseDto tokenResponseDto = TokenResponseDto.builder()
			.accessToken("Bearer " + tokenDto.getAccessToken())
			.build();

		return ResponseEntity.ok(tokenResponseDto);
	}

	@PostMapping("/api/auth/sign-out")
	public ResponseEntity<?> signOut(@RequestBody SignOutDto signOutDto) {
		var result = this.userService.signOut(signOutDto);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/api/users")
	@PreAuthorize("hasRole('MEMBER') or hasRole('MERCHANT')")
	public ResponseEntity<?> memberInfo(@ModelAttribute MemberInfoDto memberInfoDto) {
		var result = this.userService.getMemberInfo(memberInfoDto);

		return ResponseEntity.ok(result);
	}

	@PutMapping("/api/users")
	@PreAuthorize("hasRole('MEMBER') or hasRole('MERCHANT')")
	public ResponseEntity<?> memberInfoEdit(@RequestBody MemberEditDto memberEditDto) {
		var result = this.userService.editMemberInfo(memberEditDto);

		return ResponseEntity.ok(result);
	}

	@PostMapping("/api/auth/password")
	public ResponseEntity<?> sendPasswordResetEmail(@RequestBody MemberInfoDto memberInfoDto) {
		return ResponseEntity.ok(mailService.sendResetPasswordMail(memberInfoDto));
	}

	@PutMapping("/api/auth/resetPassword")
	public ResponseEntity<?> passwordReset(
		@RequestParam String uuid
		,@RequestBody MemberPasswordResetDto memberPasswordResetDto)
	{
		return ResponseEntity.ok(userService.resetPassword(uuid,memberPasswordResetDto));
	}

	@GetMapping("/api/auth/kakao/sign-in")
	public String kakaoLogin() {
		StringBuffer loginUrl = new StringBuffer();
		loginUrl.append("https://kauth.kakao.com/oauth/authorize?client_id=");
		loginUrl.append("43b7585079d42f271bc7c481ffca8f03");
		loginUrl.append("&redirect_uri=");
		loginUrl.append("https://www.fingerorder.ga/kakao_callback");
		loginUrl.append("&response_type=code");

		return "redirect:"+loginUrl.toString();
	}

	@RequestMapping("/kakao_callback")
	public ResponseEntity<?> kakaoCallback(@RequestParam String code, HttpSession session) {
		TokenDto tokenDto = userService.kakaoSignIn(code);
		return ResponseEntity.ok(tokenDto);
	}
}
