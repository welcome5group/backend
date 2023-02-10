package fingerorder.webapp.domain.member.service;

import fingerorder.webapp.domain.member.dto.MemberResponse;
import fingerorder.webapp.domain.member.dto.MemberEditNickNameRequest;
import fingerorder.webapp.domain.member.dto.MemberEditProfileRequest;
import fingerorder.webapp.domain.member.dto.MemberPasswordResetRequest;
import fingerorder.webapp.domain.member.dto.MemberWithDrawRequest;
import fingerorder.webapp.domain.member.dto.SignInRequest;
import fingerorder.webapp.domain.member.dto.SignOutRequest;
import fingerorder.webapp.domain.member.dto.SignOutResponse;
import fingerorder.webapp.domain.member.dto.SignUpRequest;
import fingerorder.webapp.domain.member.dto.TokenDto;
import fingerorder.webapp.domain.member.dto.TokenResponse;
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
import fingerorder.webapp.security.JwtTokenProvider;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.crypto.bcrypt.BCrypt;
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
	public MemberResponse signUp(SignUpRequest signUpDto) {
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
				findMember.editMemberStatus(MemberStatus.ACTIVATE);
			} else if (findMember.getStatus() == MemberStatus.UNAUTHORIZED){
				throw new NotAuthorizedException();
			}

			return this.memberRepository.save(findMember).toMemberResponse();
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
			.build();

		return this.memberRepository.save(newMember).toMemberResponse();
	}
	@Transactional
	public MemberResponse signUpSubmit(String uuid) {
		Member findMember = this.memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new UnauthorizedMemberException());

		findMember.editMemberStatus(MemberStatus.ACTIVATE);

		return this.memberRepository.save(findMember).toMemberResponse();
	}
	@Transactional
	public TokenResponse signIn(SignInRequest signInRequest) {
		if (checkInvalidEmail(signInRequest.getEmail())) {
			throw new InvalidEmailFormatException();
		}

		if (checkInvalidPassword(signInRequest.getPassword())) {
			throw new InvalidPasswordFormatException();
		}

		Member findMember = checkInvalidMember(signInRequest.getEmail());

		TokenResponse tokenResponse = null;

		try {
			// 인증 여부를 확인하기 위한 객체 생성
			UsernamePasswordAuthenticationToken authenticationToken
				= new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword());

			// 위에서 생성한 객체는 아직 인증이 되지 않은 객체가 만들어진것
			// 아래 authenticationManagerBuilder 가 실제 검증을 진행 해줌
			// 검증하기 위한 데이터가 Repository 에 제대로 들어가 있는지 확인 한 후, 위에서 만들어진 객체와 비교 할
			// 정답 객체를 생성 -> 이 작업을 loadUserByUsername 메소드가 해 준다.
			Authentication authentication =
				authenticationManagerBuilder.getObject().authenticate(authenticationToken);

			// 검증이 완료 되었으니 토큰을 만들어 준다.
			TokenDto tokenDto = this.jwtTokenProvider.getToken(authentication);

			// 만들어진 토큰을 Redis 에 담는다.
			this.saveTokenToRedis(signInRequest.getEmail(),tokenDto);

			tokenResponse = TokenResponse.builder()
				.id(findMember.getId())
				.email(findMember.getEmail())
				.nickName(findMember.getNickName())
				.memberType(findMember.getMemberType())
				.accessToken("Bearer " + tokenDto.getAccessToken())
				.build();

		}catch (Exception e) {
			throw new LoginInfoErrorException();
		}

		return tokenResponse;
	}

	@Transactional
	public TokenResponse kakaoSignIn(String code,String type) {
		String accessToken = "";
		SignInRequest tempSignInRequest = null;
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
			//sb.append("&redirect_uri=http://localhost:8080/kakao_callback?type="+type);
			sb.append("&redirect_uri=https://www.fingerorder.ga/kakao_callback?type="+type);
			sb.append("&code="+code);
			bw.write(sb.toString());
			bw.flush();

			System.out.println("kakao Sign-In code :" + code);
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String brLine = "";
			String result = "";

			while ((brLine = br.readLine()) != null) {
				result += brLine;
			}

			JSONParser parser = new JSONParser();
			JSONObject elem = (JSONObject) parser.parse(result);

			accessToken = elem.get("access_token").toString();

			MemberResponse memberResponse = getEmailByKakaoAccessToken(accessToken);

			boolean exist = memberRepository.existsByEmail(memberResponse.getEmail());

			if (exist) {
				Member findMember = this.memberRepository.findByEmail(memberResponse.getEmail())
					.orElseThrow(() -> new NoExistMemberException());

				tempSignInRequest = SignInRequest.builder()
					.email(findMember.getEmail())
					.password("kakao")
					.type(MemberType.MEMBER)
					.build();
			} else {
				Member newMember = Member.builder()
					.email(memberResponse.getEmail())
					.password(this.passwordEncoder.encode("kakao"))
					.nickName(memberResponse.getNickName())
					.memberType(MemberType.MEMBER)
					.memberSignUpType(MemberSignUpType.KAKAO)
					.status(MemberStatus.ACTIVATE)
					.build();
				memberRepository.save(newMember);

				tempSignInRequest = SignInRequest.builder()
					.email(memberResponse.getEmail())
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

		return this.signIn(tempSignInRequest);
	}

	@Transactional
	public SignOutResponse signOut(SignOutRequest signOutRequest) {
		String email = jwtTokenProvider.getEmail(signOutRequest.getAccessToken());

		if (redisTemplate.opsForValue().get("REFRESH_TOKEN:" + email) != null) {
			redisTemplate.delete("REFRESH_TOKEN:" + email);
		}

		Long expiration = jwtTokenProvider.getExpiration(signOutRequest.getAccessToken());
		redisTemplate.opsForValue()
			.set(signOutRequest.getAccessToken(),"logout",expiration,TimeUnit.MICROSECONDS);

		return SignOutResponse.builder().email(email).build();
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
	public MemberResponse getMemberInfo(String email) {
		Member findMember = checkInvalidMember(email);
		return findMember.toMemberResponse();
	}

	// user 정보 수정(nickName 밖에 없음)
	@Transactional
	public MemberResponse editMemberNickName(MemberEditNickNameRequest memberEditNickNameRequest) {
		Member findMember = checkInvalidMember(memberEditNickNameRequest.getEmail());

		boolean existNickName = this.memberRepository.existsByNickName(
			memberEditNickNameRequest.getNickName());

		if (existNickName) {
			throw new AlreadyUsageNickNameException();
		}

		findMember.editNickName(memberEditNickNameRequest.getNickName());
		this.memberRepository.save(findMember);

		return findMember.toMemberResponse();
	}

	@Transactional
	public MemberResponse editMemberProfile(MemberEditProfileRequest memberEditProfileRequest) {
		Member findMember = checkInvalidMember(memberEditProfileRequest.getEmail());

		findMember.editProfile(memberEditProfileRequest.getProfile());

		return this.memberRepository.save(findMember).toMemberResponse();
	}

	@Transactional
	public boolean resetPassword(
		String uuid,
		MemberPasswordResetRequest memberPasswordResetRequest) {
		if (checkInvalidPassword(memberPasswordResetRequest.getPassword())) {
			throw new InvalidPasswordFormatException();
		}

		Member findMember = this.memberRepository.findByUuid(uuid)
			.orElseThrow(() -> new UnauthorizedMemberException());

		String newPassword = memberPasswordResetRequest.getPassword();

		findMember.editPassword(this.passwordEncoder.encode(newPassword));

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

	private MemberResponse getEmailByKakaoAccessToken(String kakaoToken) {
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		String nickName = "";
		String email = "";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Authorization", "Bearer " + kakaoToken);

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

			System.out.println("log3");
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

		return MemberResponse.builder()
			.email(email)
			.nickName(nickName)
			.build();
	}

	@Transactional
	public MemberResponse withdrawMember(MemberWithDrawRequest memberWithDrawRequest) {
		Member findMember = checkInvalidMember(memberWithDrawRequest.getEmail());

		if (!checkCorrectPassword(memberWithDrawRequest.getPassword(), findMember.getPassword())) {
			throw new LoginInfoErrorException();
		}

		findMember.editMemberStatus(MemberStatus.DELETED);
		return this.memberRepository.save(findMember).toMemberResponse();
	}

	private boolean checkCorrectPassword(String inputPassword,String password) {
		return BCrypt.checkpw(inputPassword,password);
	}
}
