package fingerorder.webapp.domain.member.sevice;

import fingerorder.webapp.domain.member.dto.UserInfoDto;
import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
	private final MemberRepository memberRepository;
	private JavaMailSender mailSender;

	public boolean sendMail(UserInfoDto userInfoDto) {
		Optional<Member> optionalMember = this.memberRepository.findByEmail(userInfoDto.getEmail());

		if (optionalMember.isEmpty()) {
			return false;
		}

		Member findMember = optionalMember.get();

		String uuid = UUID.randomUUID().toString();
		MimeMessage message = mailSender.createMimeMessage();
		String subject = "[핑거오더] 비밀 번호 찾기 이메일 입니다.";
		//현재 프론트 테스트를 위해서 아래와 같이 링크 작성
		String url = "http://localhost:3000/findPassword?uuid=" + uuid;
		String htmlContent = "<p>핑거오더 비밀번호 재설정 이메일 입니다.</p>"
			+"<p>아래 링크를 클릭 하셔서 비밀번호를 재설정 해주세요.</p>"
			+"<div><a target ='_blank' href='"+url+"'>비밀 번호 재설정 링크</a></div>";

		findMember.giveUUID(uuid);
		this.memberRepository.save(findMember);

		try {
			MimeMessageHelper messageHelper =
				new MimeMessageHelper(message,true,"UTF-8");

			messageHelper.setSubject(subject);
			messageHelper.setTo(userInfoDto.getEmail());
			messageHelper.setFrom("mansa0805@gmail.com","핑거오더");
			messageHelper.setText(htmlContent,true);
			mailSender.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		return true;
	}
}
