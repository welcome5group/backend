package fingerorder.webapp.domain.member.service;

import fingerorder.webapp.domain.member.dto.MailSendResultDto;
import fingerorder.webapp.domain.member.dto.MemberDto;
import fingerorder.webapp.domain.member.dto.MemberInfoDto;
import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.exception.InvalidEmailFormatException;
import fingerorder.webapp.domain.member.exception.NoExistMemberException;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.domain.member.status.MemberType;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MemberRepository memberRepository;
    private final JavaMailSender mailSender;

    public void sendUserAuthMail(MemberDto memberDto) {
        if (checkInvalidEmail(memberDto.getEmail())) {
            throw new InvalidEmailFormatException();
        }

        String uuid = UUID.randomUUID().toString();
        String subject = "[핑거오더] 회원가입 인증 이메일 입니다.";
        String url = "https://fingerorder.vercel.app/login/signupauthok/uuid=" + uuid;

        if (memberDto.getMemberType() == MemberType.MEMBER) {
            url = "http://localhost:3000/signUpCheck?uuid=" + uuid;
            //url = "https://fingeroreder-order.netlify.app/signUpCheck?uuid" + uuid;
        }

        String htmlContent = "<p>핑거오더 회원가입 인증 이메일 입니다.</p>"
            + "<p>아래 링크를 클릭 하셔서 회원가입 인증을 진행해 주세요</p>"
            + "<div><a target ='_blank' href='" + url + "'>회원가입 인증 링크</a></div>";

        sendEmail(memberDto.getEmail(), uuid, subject, htmlContent);
    }

    public MailSendResultDto sendResetPasswordMail(MemberInfoDto memberInfoDto) {
        if (checkInvalidEmail(memberInfoDto.getEmail())) {
            throw new InvalidEmailFormatException();
        }

        String uuid = UUID.randomUUID().toString();
        String subject = "[핑거오더] 비밀 번호 찾기 이메일 입니다.";
        String url = "https://fingerorder.vercel.app/login/resetpassword/uuid=" + uuid;

        if (memberInfoDto.getType() == MemberType.MEMBER) {
            url = "http://localhost:3000/changePassword?uuid=" + uuid;
        }

        String htmlContent = "<p>핑거오더 비밀번호 재설정 이메일 입니다.</p>"
            + "<p>아래 링크를 클릭 하셔서 비밀번호를 재설정 해주세요.</p>"
            + "<div><a target ='_blank' href='" + url + "'>비밀 번호 재설정 링크</a></div>";

        sendEmail(memberInfoDto.getEmail(), uuid, subject, htmlContent);

        return MailSendResultDto.builder()
            .result(true)
            .build();
    }

    private void sendEmail(String email, String uuid, String subject, String htmlContent) {
        Member findMember = this.memberRepository.findByEmail(email)
            .orElseThrow(() -> new NoExistMemberException());

        MimeMessage message = mailSender.createMimeMessage();

        findMember.giveUUID(uuid);
        this.memberRepository.save(findMember);

        try {
            MimeMessageHelper messageHelper =
                new MimeMessageHelper(message, true, "UTF-8");

            messageHelper.setSubject(subject);
            messageHelper.setTo(email);
            messageHelper.setFrom("mansa0805@gmail.com", "핑거오더");
            messageHelper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkInvalidEmail(String email) {
        return !Pattern.matches("^[a-zA-Z.].+[@][a-zA-Z].+[.][a-zA-Z]{2,4}$", email);
    }
}