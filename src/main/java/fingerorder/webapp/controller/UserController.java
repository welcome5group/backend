package fingerorder.webapp.controller;

import fingerorder.webapp.service.UserService;
import fingerorder.webapp.dto.SignInDto;
import fingerorder.webapp.dto.SignUpDto;
import fingerorder.webapp.dto.UserEditDto;
import fingerorder.webapp.dto.UserInfoDto;
import fingerorder.webapp.security.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
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
	@PreAuthorize("hasRole(MEMBER) or hasRole(MERCHANT)")
	public ResponseEntity<?> userInfo(@RequestBody UserInfoDto userInfoDto) {
		var result = this.userService.getUserInfo(userInfoDto);

		return ResponseEntity.ok(result);
	}

	@PatchMapping("/api/users")
	@PreAuthorize("hasRole(MEMBER) or hasRole(MERCHANT)")
	public ResponseEntity<?> userInfoEdit(@RequestBody UserEditDto userEditDto) {
		var result = this.userService.editUserInfo(userEditDto);

		return ResponseEntity.ok(result);
	}
}
