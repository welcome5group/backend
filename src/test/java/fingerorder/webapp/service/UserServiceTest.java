package fingerorder.webapp.service;

import static fingerorder.webapp.domain.member.status.MemberType.MEMBER;
import static fingerorder.webapp.domain.member.status.MemberType.MERCHANT;
import static org.junit.jupiter.api.Assertions.assertEquals;

import fingerorder.webapp.domain.member.dto.MemberDto;
import fingerorder.webapp.domain.member.service.UserService;
import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.dto.MemberEditNickNameDto;
import fingerorder.webapp.domain.member.dto.MemberInfoDto;
import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.domain.member.service.UserService;
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
			.memberType(MEMBER)
			.updatedAt(LocalDateTime.now())
			.createdAt(LocalDateTime.now())
			.build();

		Member merchant = Member.builder()
			.email("testMerchant@naver.com")
			.nickName("testNickNameMerchant")
			.memberType(MERCHANT)
			.updatedAt(LocalDateTime.now())
			.createdAt(LocalDateTime.now())
			.build();

		MemberInfoDto userParamMember = MemberInfoDto.builder()
			.email("testMember@naver.com")
			.build();

		MemberInfoDto userParamMerchant = MemberInfoDto.builder()
			.email("testMerchant@naver.com")
			.build();

		//when
		memberRepository.save(member);
		memberRepository.save(merchant);

		//then
		MemberDto resultMember = userService.getMemberInfo(userParamMember.getEmail());
		MemberDto resultMerchant = userService.getMemberInfo(userParamMerchant.getEmail());

		assertEquals(resultMember.getEmail(),"testMember@naver.com");
		assertEquals(resultMember.getNickName(),"testNickName");
		assertEquals(resultMember.getMemberType(),MEMBER);
		assertEquals(resultMerchant.getEmail(),"testMerchant@naver.com");
		assertEquals(resultMerchant.getNickName(), "testNickNameMerchant");
		assertEquals(resultMerchant.getMemberType(), MERCHANT);
	}

	@Test
	@DisplayName("Test : Edit User Info")
	public void userEditTest() {
		Member member = Member.builder()
			.email("testMember@naver.com")
			.nickName("testNickName")
			.memberType(MEMBER)
			.updatedAt(LocalDateTime.now())
			.createdAt(LocalDateTime.now())
			.build();

		Member merchant = Member.builder()
			.email("testMerchant@naver.com")
			.nickName("testNickNameMerchant")
			.memberType(MERCHANT)
			.updatedAt(LocalDateTime.now())
			.createdAt(LocalDateTime.now())
			.build();

		MemberEditNickNameDto userEditParamMember = MemberEditNickNameDto.builder()
			.email("testMember@naver.com")
			.nickName("changedNickName")
			.build();

		MemberEditNickNameDto userEditParamMerchant = MemberEditNickNameDto.builder()
			.email("testMerchant@naver.com")
			.nickName("changedNickNameMerchant")
			.build();

		MemberInfoDto userParamMember = MemberInfoDto.builder()
			.email("testMember@naver.com")
			.build();

		MemberInfoDto userParamMerchant = MemberInfoDto.builder()
			.email("testMerchant@naver.com")
			.build();

		//when
		memberRepository.save(member);
		memberRepository.save(merchant);

		//then
		MemberDto resultMember = userService.editMemberNickName(userEditParamMember);
		MemberDto resultMerchant = userService.editMemberNickName(userEditParamMerchant);

		MemberDto checkMemberDto = userService.getMemberInfo(userParamMember.getEmail());
		MemberDto checkMerchantDto = userService.getMemberInfo(userParamMerchant.getEmail());

		assertEquals(resultMember.getNickName(),checkMemberDto.getNickName());
		assertEquals(resultMerchant.getNickName(),checkMerchantDto.getNickName());
	}
}