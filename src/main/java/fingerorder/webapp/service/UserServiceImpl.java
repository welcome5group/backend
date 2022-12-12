package fingerorder.webapp.service;

import fingerorder.webapp.dto.UserDto;
import fingerorder.webapp.dto.UserPasswordResetDto;
import fingerorder.webapp.entity.Member;
import fingerorder.webapp.dto.SignInDto;
import fingerorder.webapp.dto.SignUpDto;
import fingerorder.webapp.dto.UserEditDto;
import fingerorder.webapp.dto.UserInfoDto;
import fingerorder.webapp.repository.MemberRepository;
import fingerorder.webapp.status.UserStatus;
import fingerorder.webapp.utils.SHA256Utils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	private static final String SENDER_ADDRESS = "mansa0805@gmail.com";
	private static final String HASH_KEY = "fingerorder-manager";

	@Override
	public UserDto signUp(SignUpDto signUpDto) {
		boolean exists = this.memberRepository.existsByEmail(signUpDto.getEmail());

		if (exists) {
			throw new RuntimeException("이미 사용 중인 email 입니다.");
		}

		Member newMember = Member.builder()
			.email(signUpDto.getEmail())
			.password(this.passwordEncoder.encode(signUpDto.getPassword()))
			.nickName(signUpDto.getNickName())
			.userType(signUpDto.getType())
			.status(null)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		return this.memberRepository.save(newMember).toUserDto();
	}

	@Override
	public UserDto authenticate(SignInDto signInDto) {
		Member findMember = checkInvalidEmail(signInDto.getEmail());

		String password = findMember.getPassword();

		if (!this.passwordEncoder.matches(signInDto.getPassword(),password)) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}

		return findMember.toUserDto();
	}

	//유저 정보 가져오기
	@Override
	public UserDto getUserInfo(UserInfoDto userInfoDto) {
		Member findMember = checkInvalidEmail(userInfoDto.getEmail());

		return findMember.toUserDto();
	}

	//roles 가져오기
	@Override
	public List<String> getRoles(SignInDto signInParam) {
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_MEMBER");
		if (signInParam.getType().equals("merchant")) {
			roles.add("ROLE_MERCHANT");
		}

		return roles;
	}

	// user 정보 수정(nickName 밖에 없음)
	@Override
	public UserDto editUserInfo(UserEditDto userEditDto) {
		Member findMember = checkInvalidEmail(userEditDto.getEmail());

		findMember.editNickName(userEditDto.getNickName());

		this.memberRepository.save(findMember);

		return findMember.toUserDto();
	}

	@Override
	public boolean resetPassword(
		String uuid,
		UserPasswordResetDto userPasswordResetDto) {
		Optional<Member> optionalMember = this.memberRepository
										.findByUuid(uuid);

		if (!optionalMember.isPresent()) {
			return false;
		}

		Member findMember = optionalMember.get();
		String newPassword = userPasswordResetDto.getPassword();

		findMember.resetPassword(this.passwordEncoder.encode(newPassword));

		this.memberRepository.save(findMember);

		return true;
	}

	// security 관련
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Member> optionalMember = memberRepository.findByEmail(email);

		List<GrantedAuthority> authorities = new ArrayList<>();
		String password = "";

		authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));

		if (optionalMember.isPresent()) {
			Member member = optionalMember.get();
			password = member.getPassword();

			if (member.getUserType().equals("merchant")) {
				authorities.add(new SimpleGrantedAuthority("ROLE_MERCHANT"));
			}
		} else {
			throw new UsernameNotFoundException("couldn't find user"+ email);
		}
		return new User(email,password,authorities);
	}

	private Member checkInvalidEmail(String email) {
		return this.memberRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("등록되지 않은 사용자 입니다."));
	}

}
