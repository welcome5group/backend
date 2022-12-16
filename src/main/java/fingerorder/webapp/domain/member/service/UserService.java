package fingerorder.webapp.domain.member.service;

import fingerorder.webapp.domain.member.dto.UserDto;
import fingerorder.webapp.domain.member.dto.SignInDto;
import fingerorder.webapp.domain.member.dto.SignUpDto;
import fingerorder.webapp.domain.member.dto.UserEditDto;
import fingerorder.webapp.domain.member.dto.UserInfoDto;
import fingerorder.webapp.domain.member.dto.UserPasswordResetDto;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
	UserDto signUp(SignUpDto signUpDto);
	UserDto authenticate(SignInDto signInDto);
	UserDto getUserInfo(UserInfoDto userInfoDto);
	List<String> getRoles(SignInDto signInDto);
	UserDto editUserInfo(UserEditDto userEditDto);
	boolean resetPassword(String uuid,UserPasswordResetDto userPasswordResetDto);
}
