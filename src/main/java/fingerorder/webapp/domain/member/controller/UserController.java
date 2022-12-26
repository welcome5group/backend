package fingerorder.webapp.domain.member.controller;

import fingerorder.webapp.domain.member.dto.MemberDto;
import fingerorder.webapp.domain.member.dto.MemberEditNickNameDto;
import fingerorder.webapp.domain.member.dto.MemberEditProfileDto;
import fingerorder.webapp.domain.member.dto.MemberInfoDto;
import fingerorder.webapp.domain.member.dto.MemberPasswordResetDto;
import fingerorder.webapp.domain.member.dto.MemberWithDrawDto;
import fingerorder.webapp.domain.member.dto.SignInDto;
import fingerorder.webapp.domain.member.dto.SignOutDto;
import fingerorder.webapp.domain.member.dto.SignUpDto;
import fingerorder.webapp.domain.member.service.MailService;
import fingerorder.webapp.domain.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	@Value("${api.key}")
	private String API_KEY;

	@PostMapping("/api/auth/sign-up")
	public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpParam) {
		MemberDto unAuthMember = this.userService.signUp(signUpParam);
		this.mailService.sendUserAuthMail(unAuthMember);
		return ResponseEntity.ok(unAuthMember);
	}

	@PutMapping("/api/auth/sign-up")
	public ResponseEntity<?> signUpSubmit(@RequestParam String uuid) {
		MemberDto result = this.userService.signUpSubmit(uuid);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/api/auth/sign-in")
	public ResponseEntity<?> signIn(@RequestBody SignInDto signInDto) {
		return ResponseEntity.ok(this.userService.signIn(signInDto));
	}

	@PostMapping("/api/auth/sign-out")
	public ResponseEntity<?> signOut(@RequestBody SignOutDto signOutDto) {
		return ResponseEntity.ok(this.userService.signOut(signOutDto));
	}

	@DeleteMapping("api/users")
	public ResponseEntity<?> withdrawMember(@RequestBody MemberWithDrawDto memberWithDrawDto) {
		return ResponseEntity.ok(this.userService.withdrawMember(memberWithDrawDto));
	}

	@GetMapping("/api/users")
	@PreAuthorize("hasRole('MEMBER') or hasRole('MERCHANT')")
	public ResponseEntity<?> memberInfo(@ModelAttribute MemberInfoDto memberInfoDto) {
		var result = this.userService.getMemberInfo(memberInfoDto);
		return ResponseEntity.ok(result);
	}

	@PutMapping("/api/users/edit/nickname")
	@PreAuthorize("hasRole('MEMBER') or hasRole('MERCHANT')")
	public ResponseEntity<?> memberEditNickName(
		@RequestBody MemberEditNickNameDto memberEditNickNameDto) {
		var result = this.userService.editMemberNickName(memberEditNickNameDto);
		return ResponseEntity.ok(result);
	}

	@PutMapping("/api/users/edit/profile")
	@PreAuthorize("hasRole('MEMBER') or hasRole('MERCHANT')")
	public ResponseEntity<?> memberEditProfile(
		@RequestBody MemberEditProfileDto memberEditProfileDto) {
		var result = this.userService.editMemberProfile(memberEditProfileDto);
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
	public String kakaoLoginMember(@RequestParam String type) {
		StringBuffer loginUrl = new StringBuffer();
		loginUrl.append("https://kauth.kakao.com/oauth/authorize?client_id=");
		loginUrl.append(this.API_KEY);
		loginUrl.append("&redirect_uri=");
		if (type.equals("MEMBER")) {
			loginUrl.append("http://localhost:8080/kakao_callback?type=MEMBER");
		} else {
			loginUrl.append("http://localhost:8080/kakao_callback?type=MERCHANT");
		}
		loginUrl.append("&response_type=code");

		return "redirect:"+loginUrl.toString();
	}
}
