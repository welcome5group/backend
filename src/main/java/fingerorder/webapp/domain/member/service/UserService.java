package fingerorder.webapp.domain.member.service;

import fingerorder.webapp.domain.member.dto.MemberDto;
import fingerorder.webapp.domain.member.dto.MemberEditNickNameDto;
import fingerorder.webapp.domain.member.dto.MemberEditProfileDto;
import fingerorder.webapp.domain.member.dto.MemberInfoDto;
import fingerorder.webapp.domain.member.dto.MemberPasswordResetDto;
import fingerorder.webapp.domain.member.dto.MemberWithDrawDto;
import fingerorder.webapp.domain.member.dto.SignInDto;
import fingerorder.webapp.domain.member.dto.SignOutDto;
import fingerorder.webapp.domain.member.dto.SignOutResponseDto;
import fingerorder.webapp.domain.member.dto.SignUpDto;
import fingerorder.webapp.domain.member.dto.TokenDto;
import fingerorder.webapp.domain.member.dto.TokenResponseDto;
import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.exception.AlreadyUsageEmailException;
import fingerorder.webapp.domain.member.exception.AlreadyUsageNickNameException;
import fingerorder.webapp.domain.member.exception.InvalidEmailFormatException;
import fingerorder.webapp.domain.member.exception.InvalidPasswordFormatException;
import fingerorder.webapp.domain.member.exception.KaKaoAuthException;
import fingerorder.webapp.domain.member.exception.LoginInfoErrorException;
import fingerorder.webapp.domain.member.exception.NoExistMemberException;
import fingerorder.webapp.domain.member.exception.NotAuthorizedException;
import fingerorder.webapp.domain.member.exception.UnauthorizedMemberException;
import fingerorder.webapp.domain.member.exception.WithdrawMemberException;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.domain.member.status.MemberSignUpType;
import fingerorder.webapp.domain.member.status.MemberStatus;
import fingerorder.webapp.domain.member.status.MemberType;
import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.order.repository.OrderRepository;
import fingerorder.webapp.security.JwtTokenProvider;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice.OffsetMapping.Target.ForField.ReadOnly;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	private final RedisTemplate redisTemplate;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	@Value("${api.key}")
	private String API_KEY;

	@Transactional
	public MemberDto signUp(SignUpDto signUpDto) {
		if (checkInvalidEmail(signUpDto.getEmail())) {
			throw new InvalidEmailFormatException();
		}

		if (checkInvalidPassword(signUpDto.getPassword())) {
			throw new InvalidPasswordFormatException();
		}

		Optional<Member> optionalMember = this.memberRepository.findByEmail(signUpDto.getEmail());

		if (optionalMember.isPresent()) {
			Member findMember = optionalMember.get();

			if (findMember.getStatus() == MemberStatus.DELETED) {
				findMember.changeMemberStatus(MemberStatus.ACTIVATE);
			} else if (findMember.getStatus() == MemberStatus.UNAUTHORIZED){
				throw new NotAuthorizedException();
			}

			return this.memberRepository.save(findMember).toMemberDto();
		}

		boolean existsEmail = this.memberRepository.existsByEmail(signUpDto.getEmail());
		boolean existsNickName = this.memberRepository.existsByNickName(signUpDto.getNickName());

		if (existsEmail) {
			throw new AlreadyUsageEmailException();
		} else if (existsNickName){
			throw new AlreadyUsageNickNameException();
		}

		Member newMember = Member.builder()
			.email(signUpDto.getEmail())
			.password(this.passwordEncoder.encode(signUpDto.getPassword()))
			.nickName(signUpDto.getNickName())
			.profile(signUpDto.getProfile())
			.memberSignUpType(MemberSignUpType.NORMAL)
			.memberType(signUpDto.getType())
			.status(MemberStatus.UNAUTHORIZED)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		return this.memberRepository.save(newMember).toMemberDto();
	}
	@Transactional
	public MemberDto signUpSubmit(String uuid) {
		Member findMember = this.memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new UnauthorizedMemberException());

		findMember.changeMemberStatus(MemberStatus.ACTIVATE);

		return this.memberRepository.save(findMember).toMemberDto();
	}
	@Transactional
	public TokenResponseDto signIn(SignInDto signInDto) {
		if (checkInvalidEmail(signInDto.getEmail())) {
			throw new InvalidEmailFormatException();
		}

		if (checkInvalidPassword(signInDto.getPassword())) {
			throw new InvalidPasswordFormatException();
		}

		Member findMember = checkInvalidMember(signInDto.getEmail());

		TokenResponseDto tokenResponseDto = null;

		try {
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

			tokenResponseDto = TokenResponseDto.builder()
				.id(findMember.getId())
				.email(findMember.getEmail())
				.nickName(findMember.getNickName())
				.memberType(findMember.getMemberType())
				.accessToken("Bearer " + tokenDto.getAccessToken())
				.build();

		}catch (Exception e) {
			throw new LoginInfoErrorException();
		}

		return tokenResponseDto;
	}

	@Transactional
	public TokenResponseDto kakaoSignIn(String code,String type) {
		String accessToken = "";
		SignInDto tempSignInDto = null;
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id="+this.API_KEY);
			sb.append("&redirect_uri=https://www.fingerorder.ga/kakao_callback?type="+type);
			sb.append("&code="+code);
			bw.write(sb.toString());
			bw.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String brLine = "";
			String result = "";

			while ((brLine = br.readLine()) != null) {
				result += brLine;
			}

			JSONParser parser = new JSONParser();
			JSONObject elem = (JSONObject) parser.parse(result);

			accessToken = elem.get("access_token").toString();

			MemberDto memberDto = getEmailByKakaoAccessToken(accessToken);
			boolean exist = memberRepository.existsByEmail(memberDto.getEmail());

			if (exist) {
				Member findMember = this.memberRepository.findByEmail(memberDto.getEmail())
					.orElseThrow(() -> new NoExistMemberException());

				tempSignInDto = SignInDto.builder()
					.email(findMember.getEmail())
					.password("kakao")
					.type(MemberType.MEMBER)
					.build();
			} else {
				Member newMember = Member.builder()
					.email(memberDto.getEmail())
					.password(this.passwordEncoder.encode("kakao"))
					.nickName(memberDto.getNickName())
					.memberType(MemberType.MEMBER)
					.memberSignUpType(MemberSignUpType.KAKAO)
					.status(MemberStatus.ACTIVATE)
					.createdAt(LocalDateTime.now())
					.updatedAt(LocalDateTime.now())
					.build();
				memberRepository.save(newMember);

				tempSignInDto = SignInDto.builder()
					.email(memberDto.getEmail())
					.type(MemberType.MEMBER)
					.password("kakao")
					.build();
			}

		} catch (MalformedURLException e) {
			throw new KaKaoAuthException();
		} catch (IOException e) {
			throw new KaKaoAuthException();
		} catch (ParseException e) {
			throw new KaKaoAuthException();
		}

		return this.signIn(tempSignInDto);
	}

	@Transactional
	public SignOutResponseDto signOut(SignOutDto signOutDto) {
		String email = jwtTokenProvider.getEmail(signOutDto.getAccessToken());

		if (redisTemplate.opsForValue().get("REFRESH_TOKEN:" + email) != null) {
			redisTemplate.delete("REFRESH_TOKEN:" + email);
		}

		Long expiration = jwtTokenProvider.getExpiration(signOutDto.getAccessToken());
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
		return findMember.toMemberDto();
	}

	// user 정보 수정(nickName 밖에 없음)
	@Transactional
	public MemberDto editMemberNickName(MemberEditNickNameDto memberEditNickNameDto) {
		Member findMember = checkInvalidMember(memberEditNickNameDto.getEmail());

		boolean existNickName = this.memberRepository.existsByNickName(
			memberEditNickNameDto.getNickName());

		if (existNickName) {
			throw new AlreadyUsageNickNameException();
		}

		findMember.editNickName(memberEditNickNameDto.getNickName());
		this.memberRepository.save(findMember);

		return findMember.toMemberDto();
	}

	@Transactional
	public MemberDto editMemberProfile(MemberEditProfileDto memberEditProfileDto) {
		Member findMember = checkInvalidMember(memberEditProfileDto.getEmail());

		findMember.editProfile(memberEditProfileDto.getProfile());

		return this.memberRepository.save(findMember).toMemberDto();
	}

	@Transactional
	public boolean resetPassword(
		String uuid,
		MemberPasswordResetDto memberPasswordResetDto) {
		if (checkInvalidPassword(memberPasswordResetDto.getPassword())) {
			throw new InvalidPasswordFormatException();
		}

		Member findMember = this.memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new UnauthorizedMemberException());

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
			throw new NoExistMemberException();
		}
		return new User(email,password,authorities);
	}

	private Member checkInvalidMember(String email) {
		Member findMember = this.memberRepository.findByEmail(email)
			.orElseThrow(() -> new NoExistMemberException());

		if (findMember.getStatus() == MemberStatus.UNAUTHORIZED) {
			throw new NotAuthorizedException();
		}

		if (findMember.getStatus() == MemberStatus.DELETED) {
			throw new WithdrawMemberException();
		}

		return findMember;
	}

	private boolean checkInvalidEmail(String email) {
		return !Pattern.matches("^[a-zA-Z.].+[@][a-zA-Z].+[.][a-zA-Z]{2,4}$",email);
	}

	private boolean checkInvalidPassword(String password) {
		return Pattern.matches("^[^0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣~!@#$%^&*()-_=+,.?]{8,}$",password);
	}

	private MemberDto getEmailByKakaoAccessToken(String kakaoToken) {
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		String nickName = "";
		String email = "";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Authorization", "Bearer " + kakaoToken);
			int responseCode = conn.getResponseCode();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String brLine = "";
			String result = "";

			while ((brLine = br.readLine()) != null) {
				result += brLine;
			}

			JSONParser parser = new JSONParser();
			JSONObject elem = (JSONObject) parser.parse(result);
			JSONObject properties = (JSONObject) elem.get("properties");
			JSONObject kakaoAccount = (JSONObject) elem.get("kakao_account");

			nickName = properties.get("nickname").toString();
			email = kakaoAccount.get("email").toString();

			boolean existByNickName = memberRepository.existsByNickName(nickName);
			List<Member> members = memberRepository.findAll();

			if (existByNickName) {
				String defaultNickName = "fingerOrder" + Integer.toString(members.size());
				nickName = defaultNickName;
			}
		} catch (MalformedURLException e) {
			throw new KaKaoAuthException();
		} catch (IOException e) {
			throw new KaKaoAuthException();
		} catch (ParseException e) {
			throw new KaKaoAuthException();
		}

		return MemberDto.builder()
			.email(email)
			.nickName(nickName)
			.build();
	}

	@Transactional
	public MemberDto withdrawMember(MemberWithDrawDto memberWithDrawDto) {
		Member findMember = checkInvalidMember(memberWithDrawDto.getEmail());

		if (!checkCorrectPassword(memberWithDrawDto.getPassword(), findMember.getPassword())) {
			throw new LoginInfoErrorException();
		}

		findMember.changeMemberStatus(MemberStatus.DELETED);
		return this.memberRepository.save(findMember).toMemberDto();
	}

	private boolean checkCorrectPassword(String inputPassword,String password) {
		if (this.passwordEncoder.encode(inputPassword).equals(password)) {
			return true;
		}
		return false;
	}
}
