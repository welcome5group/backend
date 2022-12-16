package fingerorder.webapp.service;

import static fingerorder.webapp.domain.member.entity.UserType.MEMBER;
import static fingerorder.webapp.domain.member.entity.UserType.MERCHANT;
import static org.junit.jupiter.api.Assertions.*;

import fingerorder.webapp.domain.member.dto.UserDto;
import fingerorder.webapp.domain.member.sevice.UserService;
import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.dto.UserEditDto;
import fingerorder.webapp.domain.member.dto.UserInfoDto;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {
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
			.userType(MEMBER)
			.updatedAt(LocalDateTime.now())
			.createdAt(LocalDateTime.now())
			.build();

		Member merchant = Member.builder()
			.email("testMerchant@naver.com")
			.nickName("testNickNameMerchant")
			.userType(MERCHANT)
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
		assertEquals(resultMember.getUserType(),MEMBER);
		assertEquals(resultMerchant.getEmail(),"testMerchant@naver.com");
		assertEquals(resultMerchant.getNickName(), "testNickNameMerchant");
		assertEquals(resultMerchant.getUserType(), MERCHANT);
	}

	@Test
	@DisplayName("Test : Edit User Info")
	public void userEditTest() {
		Member member = Member.builder()
			.email("testMember@naver.com")
			.nickName("testNickName")
			.userType(MEMBER)
			.updatedAt(LocalDateTime.now())
			.createdAt(LocalDateTime.now())
			.build();

		Member merchant = Member.builder()
			.email("testMerchant@naver.com")
			.nickName("testNickNameMerchant")
			.userType(MERCHANT)
			.updatedAt(LocalDateTime.now())
			.createdAt(LocalDateTime.now())
			.build();

		UserEditDto userEditParamMember = UserEditDto.builder()
			.email("testMember@naver.com")
			.nickName("changedNickName")
			.type(MEMBER)
			.build();

		UserEditDto userEditParamMerchant = UserEditDto.builder()
			.email("testMerchant@naver.com")
			.nickName("changedNickNameMerchant")
			.type(MERCHANT)
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
}