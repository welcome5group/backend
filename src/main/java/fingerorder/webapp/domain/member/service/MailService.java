package fingerorder.webapp.domain.member.service;

import fingerorder.webapp.domain.member.dto.UserInfoDto;

public interface MailService {
	boolean sendMail(UserInfoDto userInfoDto);
}
