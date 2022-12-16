package fingerorder.webapp.domain.member.controller;

import fingerorder.webapp.domain.member.dto.SignInDto;
import fingerorder.webapp.domain.member.dto.SignUpDto;
import fingerorder.webapp.domain.member.dto.UserEditDto;
import fingerorder.webapp.domain.member.dto.UserInfoDto;
import fingerorder.webapp.domain.member.dto.UserPasswordResetDto;
import fingerorder.webapp.security.JwtTokenProvider;
import fingerorder.webapp.domain.member.service.MailService;
import fingerorder.webapp.domain.member.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final MailService mailService;
	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/api/auth/sign-up")
	public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpParam) {
		var result = this.userService.signUp(signUpParam);

		return ResponseEntity.ok(result);
	}

	@PostMapping("/api/auth/sign-in")
	public ResponseEntity<?> signIn(@RequestBody SignInDto signInDto) {
		var result = this.userService.authenticate(signInDto);
		List<String> roles = this.userService.getRoles(signInDto);
		var token = this.jwtTokenProvider.genToken(signInDto.getEmail(),roles);

		return ResponseEntity.ok(token);
	}

	@GetMapping("/api/users")
	@PreAuthorize("hasRole('MEMBER') or hasRole('MERCHANT')")
	public ResponseEntity<?> userInfo(@ModelAttribute UserInfoDto userInfoDto) {
		var result = this.userService.getUserInfo(userInfoDto);

		return ResponseEntity.ok(result);
	}

	@PutMapping("/api/users")
	@PreAuthorize("hasRole('MEMBER') or hasRole('MERCHANT')")
	public ResponseEntity<?> userInfoEdit(@RequestBody UserEditDto userEditDto) {
		var result = this.userService.editUserInfo(userEditDto);

		return ResponseEntity.ok(result);
	}

	@PostMapping("/api/auth/password")
	public ResponseEntity<?> sendPasswordResetEmail(@RequestBody UserInfoDto userInfoDto) {
		return ResponseEntity.ok(mailService.sendMail(userInfoDto));
	}

	@PutMapping("/findPassword")
	public ResponseEntity<?> passwordReset(
		@RequestParam String uuid
		,@RequestBody UserPasswordResetDto userPasswordResetDto)
	{
		return ResponseEntity.ok(userService.resetPassword(uuid,userPasswordResetDto));
	}
}