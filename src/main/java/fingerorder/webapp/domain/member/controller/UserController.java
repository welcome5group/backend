package fingerorder.webapp.domain.member.controller;

import fingerorder.webapp.domain.member.dto.SignInDto;
import fingerorder.webapp.domain.member.dto.SignOutDto;
import fingerorder.webapp.domain.member.dto.SignUpDto;
import fingerorder.webapp.domain.member.dto.TokenDto;
import fingerorder.webapp.domain.member.dto.MemberEditDto;
import fingerorder.webapp.domain.member.dto.MemberInfoDto;
import fingerorder.webapp.domain.member.dto.MemberPasswordResetDto;
import fingerorder.webapp.domain.member.service.MailService;
import fingerorder.webapp.domain.member.service.UserService;
import fingerorder.webapp.security.JwtTokenProvider;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/api/auth/sign-up")
	public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpParam) {
		var result = this.userService.signUp(signUpParam);

		return ResponseEntity.ok(result);
	}

	@PostMapping("/api/auth/sign-in")
	public ResponseEntity<?> signIn(@RequestBody SignInDto signInDto) {
		TokenDto tokenDto = this.userService.signIn(signInDto);
		return ResponseEntity.ok(tokenDto);
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
	public ResponseEntity<?> memberInfoEdit(@RequestBody MemberEditDto userEditDto) {
		var result = this.userService.editMemberInfo(userEditDto);

		return ResponseEntity.ok(result);
	}

	@PostMapping("/api/auth/password")
	@PreAuthorize("hasRole('MEMBER') or hasRole('MERCAHNT')")
	public ResponseEntity<?> sendPasswordResetEmail(@RequestBody MemberInfoDto memberInfoDto) {
		return ResponseEntity.ok(mailService.sendMail(memberInfoDto));
	}

	@PutMapping("/findPassword")
	public ResponseEntity<?> passwordReset(
		@RequestParam String uuid
		,@RequestBody MemberPasswordResetDto memberPasswordResetDto)
	{
		return ResponseEntity.ok(userService.resetPassword(uuid, memberPasswordResetDto));
	}
}
