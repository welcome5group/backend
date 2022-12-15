package fingerorder.webapp.service;

import fingerorder.webapp.dto.UserInfoDto;

public interface MailService {
	boolean sendMail(UserInfoDto userInfoDto);
}
