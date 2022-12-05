package fingerorder.webapp.Service;

import fingerorder.webapp.dto.UserDto;
import fingerorder.webapp.entity.Member;
import fingerorder.webapp.entity.Merchant;
import fingerorder.webapp.parameter.SignInParam;
import fingerorder.webapp.parameter.SignUpParam;
import fingerorder.webapp.parameter.UserEditParam;
import fingerorder.webapp.parameter.UserParam;
import fingerorder.webapp.repository.MemberRepository;
import fingerorder.webapp.repository.MerchantRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
	private final MerchantRepository merchantRepository;

	@Override
	public UserDto signUp(SignUpParam signUpParam) {
		boolean exists = false;
		if (signUpParam.getType().equals("member")) {
			exists = this.memberRepository.existsByEmail(signUpParam.getEmail());
		} else {
			exists = this.merchantRepository.existsByEmail(signUpParam.getEmail());
		}

		if (exists) {
			throw new RuntimeException("이미 사용 중인 email 입니다.");
		}

		if (signUpParam.getType().equals("member")) {
			Member newMember = Member.builder()
				.email(signUpParam.getEmail())
				.password(this.passwordEncoder.encode(signUpParam.getPassword()))
				.nickname(signUpParam.getNickName())
				.created_at(LocalDateTime.now())
				.updated_at(LocalDateTime.now())
				.build();

			return this.memberRepository.save(newMember).toUserDto();
		} else {
			Merchant newMerchant = Merchant.builder()
				.email(signUpParam.getEmail())
				.password(this.passwordEncoder.encode(signUpParam.getPassword()))
				.nickname(signUpParam.getNickName())
				.created_at(LocalDateTime.now())
				.updated_at(LocalDateTime.now())
				.build();
			return this.merchantRepository.save(newMerchant).toUserDto();
		}
	}

	@Override
	public UserDto authenticate(SignInParam signInParam) {
		UserDto userDto = null;
		String password = "";

		if (signInParam.getType().equals("member")) {
			Member findMember = this.memberRepository.findByEmail(signInParam.getEmail())
				.orElseThrow(() -> new RuntimeException("등록되지 않은 사용자 입니다."));
			userDto = findMember.toUserDto();
			password = findMember.getPassword();
		} else {
			Merchant findMerchant = this.merchantRepository.findByEmail(signInParam.getEmail())
				.orElseThrow(() -> new RuntimeException("등록되지 않은 사용자 입니다."));
			userDto = findMerchant.toUserDto();
			password = findMerchant.getPassword();
		}

		if (!this.passwordEncoder.matches(signInParam.getPassword(),password)) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}

		return userDto;
	}

	//유저 정보 가져오기
	@Override
	public UserDto getUserInfo(UserParam userParam) {
		boolean exist = false;
		if (userParam.getType().equals("member")) {
			exist = this.memberRepository.existsByEmail(userParam.getEmail());
		} else {
			exist = this.merchantRepository.existsByEmail(userParam.getEmail());
		}

		if (!exist) {
			throw new RuntimeException("등록되지 않은 사용자 입니다.");
		}

		UserDto result = new UserDto();

		if (userParam.getType().equals("member")) {
			Member findMember = this.memberRepository.findByEmail(userParam.getEmail())
				.orElseThrow(() -> new RuntimeException("등록되지 않은 사용자 입니다."));
			result = findMember.toUserDto();
		} else {
			Merchant findMerchant = this.merchantRepository.findByEmail(userParam.getEmail())
				.orElseThrow(() -> new RuntimeException("등록되지 않은 사용자 입니다."));
			result = findMerchant.toUserDto();
		}

		return result;
	}

	//roles 가져오기
	@Override
	public List<String> getRoles(SignInParam signInParam) {
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_MEMBER");
		if (signInParam.getType().equals("merchant")) {
			roles.add("ROLE_MERCHANT");
		}

		return roles;
	}

	// user 정보 수정(nickName 밖에 없음)
	@Override
	public UserDto editUserInfo(UserEditParam userEditParam) {
		boolean exist = false;
		if (userEditParam.getType().equals("member")) {
			exist = this.memberRepository.existsByEmail(userEditParam.getEmail());
		} else {
			exist = this.merchantRepository.existsByEmail(userEditParam.getEmail());
		}

		if (!exist) {
			throw new RuntimeException("등록되지 않은 사용자 입니다.");
		}

		return null;
	}

	// security 관련
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Member> optionalMember = memberRepository.findByEmail(email);
		Optional<Merchant> optionalMerchant = merchantRepository.findByEmail(email);

		List<GrantedAuthority> authorities = new ArrayList<>();
		String password = "";

		authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));

		if (optionalMember.isPresent()) {
			Member member = optionalMember.get();
			password = member.getPassword();

		} else if (optionalMerchant.isPresent()){
			Merchant merchant = optionalMerchant.get();
			password = merchant.getPassword();
			authorities.add(new SimpleGrantedAuthority("ROLE_MERCHANT"));
		} else {
			throw new UsernameNotFoundException("couldn't find user"+ email);

		}

		return new User(email,password,authorities);
	}
}
