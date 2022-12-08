package fingerorder.webapp.service;

import fingerorder.webapp.dto.UserInfoDto;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService{
	private JavaMailSender mailSender;
	private static final String SENDER_ADDRESS = "fingerorder@gmail.com";
	private static final String HASH_KEY = "fingerorder-manager";

	@Override
	public void sendMail(UserInfoDto userInfoDto) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		String subject = "[핑거오더] 비밀 번호 찾기 이메일 입니다.";
		String htmlContent = "<p>핑거오더 비밀번호 재설정 이메일 입니다.</p>"
			+"<p>아래 링크를 클릭 하셔서 비밀번호를 재설정 해주세요.</p>"
			+"<div><a target ='_blank' href='#'>비밀 전호 재설정 링크</a></div>";

		mailMessage.setTo(userInfoDto.getEmail());
		mailMessage.setFrom(this.SENDER_ADDRESS);
		mailMessage.setSubject(subject);
		mailMessage.setText(htmlContent);
	}

	public static String encrypt(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] passBytes = s.getBytes();
			md.reset();
			byte[] digested = md.digest(passBytes);
			StringBuilder sb = new StringBuilder();
			return null;
		} catch (Exception e) {
			return s;
		}
	}
}
