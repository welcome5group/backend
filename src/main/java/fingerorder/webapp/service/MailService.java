package fingerorder.webapp.service;

import fingerorder.webapp.dto.UserInfoDto;

public interface MailService {
	void sendMail(UserInfoDto userInfoDto);
}
