package fingerorder.webapp.service;

import static org.junit.jupiter.api.Assertions.*;

import fingerorder.webapp.dto.UserDto;
import fingerorder.webapp.entity.Member;
import fingerorder.webapp.entity.Merchant;
import fingerorder.webapp.parameter.UserEditParam;
import fingerorder.webapp.parameter.UserParam;
import fingerorder.webapp.repository.MemberRepository;
import fingerorder.webapp.repository.MerchantRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceImplTest {
	@Autowired
	UserService userService;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MerchantRepository merchantRepository;

	@Test
	@DisplayName("Test : Get User Info")
	public void userInfoTest() {
		//given
		Member member = Member.builder()
			.email("testMember@naver.com")
			.nickName("testNickName")
			.updated_at(LocalDateTime.now())
			.created_at(LocalDateTime.now())
			.build();

		Merchant merchant = Merchant.builder()
			.email("testMerchant@naver.com")
			.nickName("testNickNameMerchant")
			.updated_at(LocalDateTime.now())
			.created_at(LocalDateTime.now())
			.build();

		UserParam userParamMember = UserParam.builder()
			.email("testMember@naver.com")
			.type("member")
			.build();

		UserParam userParamMerchant = UserParam.builder()
			.email("testMerchant@naver.com")
			.type("merchant")
			.build();

		//when
		memberRepository.save(member);
		merchantRepository.save(merchant);

		//then
		UserDto resultMember = userService.getUserInfo(userParamMember);
		UserDto resultMerchant = userService.getUserInfo(userParamMerchant);

		assertEquals(resultMember.getEmail(),"testMember@naver.com");
		assertEquals(resultMember.getNickName(),"testNickName");
		assertEquals(resultMerchant.getEmail(),"testMerchant@naver.com");
		assertEquals(resultMerchant.getNickName(), "testNickNameMerchant");
	}

	@Test
	@DisplayName("Test : Edit User Info")
	public void userEditTest() {
		Member member = Member.builder()
			.email("testMember@naver.com")
			.nickName("testNickName")
			.updated_at(LocalDateTime.now())
			.created_at(LocalDateTime.now())
			.build();

		Merchant merchant = Merchant.builder()
			.email("testMerchant@naver.com")
			.nickName("testNickNameMerchant")
			.updated_at(LocalDateTime.now())
			.created_at(LocalDateTime.now())
			.build();

		UserEditParam userEditParamMember = UserEditParam.builder()
			.email("testMember@naver.com")
			.nickName("changedNickName")
			.type("member")
			.build();

		UserEditParam userEditParamMerchant = UserEditParam.builder()
			.email("testMerchant@naver.com")
			.nickName("changedNickNameMerchant")
			.type("merchant")
			.build();

		UserParam userParamMember = UserParam.builder()
			.email("testMember@naver.com")
			.type("member")
			.build();

		UserParam userParamMerchant = UserParam.builder()
			.email("testMerchant@naver.com")
			.type("merchant")
			.build();

		//when
		memberRepository.save(member);
		merchantRepository.save(merchant);

		//then
		UserDto resultMember = userService.editUserInfo(userEditParamMember);
		UserDto resultMerchant = userService.editUserInfo(userEditParamMerchant);

		UserDto checkMemberDto = userService.getUserInfo(userParamMember);
		UserDto checkMerchantDto = userService.getUserInfo(userParamMerchant);

		assertEquals(resultMember.getNickName(),checkMemberDto.getNickName());
		assertEquals(resultMerchant.getNickName(),checkMerchantDto.getNickName());
	}
}