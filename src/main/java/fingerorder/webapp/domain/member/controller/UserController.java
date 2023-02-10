package fingerorder.webapp.domain.member.controller;

import fingerorder.webapp.domain.member.dto.MailSendResponse;
import fingerorder.webapp.domain.member.dto.MemberResponse;
import fingerorder.webapp.domain.member.dto.MemberEditNickNameRequest;
import fingerorder.webapp.domain.member.dto.MemberEditProfileRequest;
import fingerorder.webapp.domain.member.dto.MemberInfoRequest;
import fingerorder.webapp.domain.member.dto.MemberPasswordResetRequest;
import fingerorder.webapp.domain.member.dto.MemberWithDrawRequest;
import fingerorder.webapp.domain.member.dto.SignInRequest;
import fingerorder.webapp.domain.member.dto.SignOutRequest;
import fingerorder.webapp.domain.member.dto.SignOutResponse;
import fingerorder.webapp.domain.member.dto.SignUpRequest;
import fingerorder.webapp.domain.member.dto.TokenResponse;
import fingerorder.webapp.domain.member.service.MailService;
import fingerorder.webapp.domain.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseEntity<MemberResponse> signUp(@RequestBody SignUpRequest signUpParam) {
		MemberResponse unAuthMember = this.userService.signUp(signUpParam);
		this.mailService.sendUserAuthMail(unAuthMember);
		return ResponseEntity.ok(unAuthMember);
	}

	@PutMapping("/api/auth/sign-up")
	public ResponseEntity<MemberResponse> signUpSubmit(@RequestParam String uuid) {
		MemberResponse result = this.userService.signUpSubmit(uuid);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/api/auth/sign-in")
	public ResponseEntity<TokenResponse> signIn(@RequestBody SignInRequest signInRequest) {
		return ResponseEntity.ok(this.userService.signIn(signInRequest));
	}

	@PostMapping("/api/auth/sign-out")
	public ResponseEntity<SignOutResponse> signOut(@RequestBody SignOutRequest signOutRequest) {
		return ResponseEntity.ok(this.userService.signOut(signOutRequest));
	}

	@PostMapping("api/users/delete")
	public ResponseEntity<MemberResponse> withdrawMember(
		@RequestBody MemberWithDrawRequest memberWithDrawRequest) {
		return ResponseEntity.ok(this.userService.withdrawMember(memberWithDrawRequest));
	}

	@GetMapping("/api/users")
	@PreAuthorize("hasRole('MEMBER') or hasRole('MERCHANT')")
	public ResponseEntity<MemberResponse> memberInfo(@RequestParam String email) {
		return ResponseEntity.ok(this.userService.getMemberInfo(email));
	}

	@PutMapping("/api/users/edit/nickname")
	@PreAuthorize("hasRole('MEMBER') or hasRole('MERCHANT')")
	public ResponseEntity<MemberResponse> memberEditNickName(
		@RequestBody MemberEditNickNameRequest memberEditNickNameRequest) {
		return ResponseEntity.ok(this.userService.editMemberNickName(memberEditNickNameRequest));
	}

	@PutMapping("/api/users/edit/profile")
	@PreAuthorize("hasRole('MEMBER') or hasRole('MERCHANT')")
	public ResponseEntity<MemberResponse> memberEditProfile(
		@RequestBody MemberEditProfileRequest memberEditProfileRequest) {
		return ResponseEntity.ok(this.userService.editMemberProfile(memberEditProfileRequest));
	}

	@PostMapping("/api/auth/password")
	public ResponseEntity<MailSendResponse> sendPasswordResetEmail(
		@RequestBody MemberInfoRequest memberInfoRequest) {
		return ResponseEntity.ok(mailService.sendResetPasswordMail(memberInfoRequest));
	}

	@PutMapping("/api/auth/resetPassword")
	public ResponseEntity<Boolean> passwordReset(
		@RequestParam String uuid
		,@RequestBody MemberPasswordResetRequest memberPasswordResetRequest)
	{
		return ResponseEntity.ok(userService.resetPassword(uuid, memberPasswordResetRequest));
	}

	@GetMapping("/api/auth/kakao/sign-in")
	public String kakaoLoginMember(@RequestParam String type) {
		StringBuffer loginUrl = new StringBuffer();
		loginUrl.append("https://kauth.kakao.com/oauth/authorize?client_id=");
		loginUrl.append(this.API_KEY);
		loginUrl.append("&redirect_uri=");
		if (type.equals("MEMBER")) {
			//loginUrl.append("http://localhost:8080/kakao_callback?type=MEMBER");
			loginUrl.append("https://www.fingerorder.ga/kakao_callback?type=MEMBER");
		} else {
			//loginUrl.append("http://localhost:8080/kakao_callback?type=MERCHANT");
			loginUrl.append("https://www.fingerorder.ga/kakao_callback?type=MERCHANT");
		}
		loginUrl.append("&response_type=code");

		return "redirect:"+loginUrl.toString();
	}
}
