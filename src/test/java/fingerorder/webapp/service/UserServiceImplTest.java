package fingerorder.webapp.service;

import static org.junit.jupiter.api.Assertions.*;

import fingerorder.webapp.dto.UserDto;
import fingerorder.webapp.entity.Member;
import fingerorder.webapp.dto.UserEditDto;
import fingerorder.webapp.dto.UserInfoDto;
import fingerorder.webapp.repository.MemberRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceImplTest {
	@Autowired
	UserService userService;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("Test : Get User Info")
	public void userInfoTest() {
		//given
		Member member = Member.builder()
			.email("testMember@naver.com")
			.nickName("testNickName")
			.userType("member")
			.updatedAt(LocalDateTime.now())
			.createdAt(LocalDateTime.now())
			.build();

		Member merchant = Member.builder()
			.email("testMerchant@naver.com")
			.nickName("testNickNameMerchant")
			.userType("merchant")
			.updatedAt(LocalDateTime.now())
			.createdAt(LocalDateTime.now())
			.build();

		UserInfoDto userParamMember = UserInfoDto.builder()
			.email("testMember@naver.com")
			.build();

		UserInfoDto userParamMerchant = UserInfoDto.builder()
			.email("testMerchant@naver.com")
			.build();

		//when
		memberRepository.save(member);
		memberRepository.save(merchant);

		//then
		UserDto resultMember = userService.getUserInfo(userParamMember);
		UserDto resultMerchant = userService.getUserInfo(userParamMerchant);

		assertEquals(resultMember.getEmail(),"testMember@naver.com");
		assertEquals(resultMember.getNickName(),"testNickName");
		assertEquals(resultMember.getUserType(),"member");
		assertEquals(resultMerchant.getEmail(),"testMerchant@naver.com");
		assertEquals(resultMerchant.getNickName(), "testNickNameMerchant");
		assertEquals(resultMerchant.getUserType(), "merchant");
	}

	@Test
	@DisplayName("Test : Edit User Info")
	public void userEditTest() {
		Member member = Member.builder()
			.email("testMember@naver.com")
			.nickName("testNickName")
			.userType("member")
			.updatedAt(LocalDateTime.now())
			.createdAt(LocalDateTime.now())
			.build();

		Member merchant = Member.builder()
			.email("testMerchant@naver.com")
			.nickName("testNickNameMerchant")
			.userType("merchant")
			.updatedAt(LocalDateTime.now())
			.createdAt(LocalDateTime.now())
			.build();

		UserEditDto userEditParamMember = UserEditDto.builder()
			.email("testMember@naver.com")
			.nickName("changedNickName")
			.type("member")
			.build();

		UserEditDto userEditParamMerchant = UserEditDto.builder()
			.email("testMerchant@naver.com")
			.nickName("changedNickNameMerchant")
			.type("merchant")
			.build();

		UserInfoDto userParamMember = UserInfoDto.builder()
			.email("testMember@naver.com")
			.build();

		UserInfoDto userParamMerchant = UserInfoDto.builder()
			.email("testMerchant@naver.com")
			.build();

		//when
		memberRepository.save(member);
		memberRepository.save(merchant);

		//then
		UserDto resultMember = userService.editUserInfo(userEditParamMember);
		UserDto resultMerchant = userService.editUserInfo(userEditParamMerchant);

		UserDto checkMemberDto = userService.getUserInfo(userParamMember);
		UserDto checkMerchantDto = userService.getUserInfo(userParamMerchant);

		assertEquals(resultMember.getNickName(),checkMemberDto.getNickName());
		assertEquals(resultMerchant.getNickName(),checkMerchantDto.getNickName());
	}

	@Test
	@DisplayName("Test : Init User Password")
	public void initPasswordTest() {
		//given
		Member member = Member.builder()
			.email("testMember@naver.com")
			.nickName("testNickName")
			.userType("member")
			.password(this.passwordEncoder.encode("memberPassword"))
			.updatedAt(LocalDateTime.now())
			.createdAt(LocalDateTime.now())
			.build();

		Member merchant = Member.builder()
			.email("testMerchant@naver.com")
			.nickName("testNickNameMerchant")
			.userType("merchant")
			.password(this.passwordEncoder.encode("merchantPassword"))
			.updatedAt(LocalDateTime.now())
			.createdAt(LocalDateTime.now())
			.build();

		UserInfoDto userParamMember = UserInfoDto.builder()
			.email("testMember@naver.com")
			.build();

		UserInfoDto userParamMerchant = UserInfoDto.builder()
			.email("testMerchant@naver.com")
			.build();

		//when
		memberRepository.save(member);
		memberRepository.save(merchant);

		//then
		String resultMemberInitPassword = this.userService.resetPassword(userParamMember);
		String resultMerchantInitPassword = this.userService.resetPassword(userParamMerchant);

		assertNotEquals("memberPassword",resultMemberInitPassword);
		assertNotEquals("merchantPassword",resultMerchantInitPassword);
	}
}