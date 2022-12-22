package fingerorder.webapp.domain.member.service;

import fingerorder.webapp.dto.UserInfoDto;

public interface MailService {

    void sendMail(UserInfoDto userInfoDto);
}
