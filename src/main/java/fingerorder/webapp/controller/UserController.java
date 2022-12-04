package fingerorder.webapp.controller;

import fingerorder.webapp.Service.UserService;
import fingerorder.webapp.parameter.SignInParam;
import fingerorder.webapp.parameter.SignUpParam;
import fingerorder.webapp.security.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/api/auth/sign-up")
	public ResponseEntity<?> signUp(@RequestBody SignUpParam signUpParam) {
		var result = this.userService.signUp(signUpParam);

		return ResponseEntity.ok(result);
	}

	@PostMapping("/api/auth/sign-in")
	public ResponseEntity<?> signIn(@RequestBody SignInParam signInParam) {
		var result = this.userService.authenticate(signInParam);
		List<String> roles = this.userService.getRoles(signInParam);
		var token = this.jwtTokenProvider.genToken(signInParam.getEmail(),roles);

		return ResponseEntity.ok(token);
	}

}
