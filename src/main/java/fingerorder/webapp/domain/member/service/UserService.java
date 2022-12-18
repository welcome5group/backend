package fingerorder.webapp.domain.member.service;

import fingerorder.webapp.domain.member.dto.SignInDto;
import fingerorder.webapp.domain.member.dto.SignOutDto;
import fingerorder.webapp.domain.member.dto.SignOutResponseDto;
import fingerorder.webapp.domain.member.dto.SignUpDto;
import fingerorder.webapp.domain.member.dto.TokenDto;
import fingerorder.webapp.domain.member.dto.MemberDto;
import fingerorder.webapp.domain.member.dto.MemberEditDto;
import fingerorder.webapp.domain.member.dto.MemberInfoDto;
import fingerorder.webapp.domain.member.dto.MemberPasswordResetDto;
import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.status.MemberStatus;
import fingerorder.webapp.domain.member.status.MemberType;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.security.JwtTokenProvider;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	private final RedisTemplate redisTemplate;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	public MemberDto signUp(SignUpDto signUpDto) {
		if (!checkInvalidEmail(signUpDto.getEmail())) {
			throw new RuntimeException("잘못된 이메일 형식");
		}

		if (!checkInvalidPassword(signUpDto.getPassword())) {
			throw new RuntimeException("잘못된 비밀번호 형식");
		}

		boolean exists = this.memberRepository.existsByEmail(signUpDto.getEmail());

		if (exists) {
			throw new RuntimeException("이미 사용 중인 email 입니다.");
		}

		Member newMember = Member.builder()
			.email(signUpDto.getEmail())
			.password(this.passwordEncoder.encode(signUpDto.getPassword()))
			.nickName(signUpDto.getNickName())
			.memberType(signUpDto.getType())
			.status(MemberStatus.ACTIVATE)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		return this.memberRepository.save(newMember).toUserDto();
	}

	public TokenDto signIn(SignInDto signInDto) {
		if (!checkInvalidEmail(signInDto.getEmail())) {
			throw new RuntimeException("잘못된 이메일 형식");
		}

		if (!checkInvalidPassword(signInDto.getPassword())) {
			throw new RuntimeException("잘못된 비밀번호 형식");
		}

		boolean exist = this.memberRepository.existsByEmail(signInDto.getEmail());

		if (!exist) {
			throw new RuntimeException("존재하지 않는 사용자 입니다.");
		}

		// 인증 여부를 확인하기 위한 객체 생성
		UsernamePasswordAuthenticationToken authenticationToken
			= new UsernamePasswordAuthenticationToken(signInDto.getEmail(),signInDto.getPassword());

		// 위에서 생성한 객체는 아직 인증이 되지 않은 객체가 만들어진것
		// 아래 authenticationManagerBuilder 가 실제 검증을 진행 해줌
		// 검증하기 위한 데이터가 Repository 에 제대로 들어가 있는지 확인 한 후, 위에서 만들어진 객체와 비교 할
		// 정답 객체를 생성 -> 이 작업을 loadUserByUsername 메소드가 해 준다.
		Authentication authentication =
			authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// 검증이 완료 되었으니 토큰을 만들어 준다.
		TokenDto tokenDto = this.jwtTokenProvider.getToken(authentication);

		// 만들어진 토큰을 Redis 에 담는다.
		this.saveTokenToRedis(signInDto.getEmail(),tokenDto);

		return tokenDto;
	}

	public SignOutResponseDto signOut(SignOutDto signOutDto) {
		String email = jwtTokenProvider.getEmail(signOutDto.getAccessToken());

		if (redisTemplate.opsForValue().get("REFRESH_TOKEN:" + email) != null) {
			redisTemplate.delete("REFRESH_TOKEN:" + email);
		}

		Long expiration = jwtTokenProvider.getExpiration(signOutDto.getRefreshToken());
		redisTemplate.opsForValue()
			.set(signOutDto.getAccessToken(),"logout",expiration,TimeUnit.MICROSECONDS);

		return SignOutResponseDto.builder().email(email).build();
	}

	// refresh 토큰 redis에 저장
	public void saveTokenToRedis(String email, TokenDto tokenDto) {
		// ExpirationTime 설정을 통해 해당 시간이 만료되면 Redis에서 자동 삭제
		redisTemplate.opsForValue()
			.set("REFRESH_TOKEN:" + email,
				tokenDto.getRefreshToken(),
				tokenDto.getRefreshTokenExpirationTime(),
				TimeUnit.MILLISECONDS);
	}

	//유저 정보 가져오기
	public MemberDto getMemberInfo(MemberInfoDto memberInfoDto) {
		Member findMember = checkInvalidMember(memberInfoDto.getEmail());

		return findMember.toUserDto();
	}

	// user 정보 수정(nickName 밖에 없음)
	public MemberDto editMemberInfo(MemberEditDto userEditDto) {
		Member findMember = checkInvalidMember(userEditDto.getEmail());

		findMember.editNickName(userEditDto.getNickName());

		this.memberRepository.save(findMember);

		return findMember.toUserDto();
	}

	public boolean resetPassword(
		String uuid,
		MemberPasswordResetDto memberPasswordResetDto) {
		if (!checkInvalidPassword(memberPasswordResetDto.getPassword())) {
			throw new RuntimeException("잘못된 비밀빈호 형식");
		}

		Member findMember = this.memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new RuntimeException("인증되지 않은 사용자 입니다."));

		String newPassword = memberPasswordResetDto.getPassword();

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

			if (member.getMemberType() == MemberType.MERCHANT) {
				authorities.add(new SimpleGrantedAuthority("ROLE_MERCHANT"));
			}
		} else {
			throw new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."+ email);
		}
		return new User(email,password,authorities);
	}

	private Member checkInvalidMember(String email) {
		return this.memberRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("등록되지 않은 사용자 입니다."));
	}

	private boolean checkInvalidEmail(String email) {
		return Pattern.matches("[a-zA-Z.].+[@][a-zA-Z].+[.][a-zA-Z]{2,4}$",email);
	}

	private boolean checkInvalidPassword(String password) {
		return Pattern.matches("^[0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣~!@#$%^&*()-_=+,.?]*$",password);
	}
}
