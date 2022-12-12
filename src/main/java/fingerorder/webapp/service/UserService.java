package fingerorder.webapp.service;

import fingerorder.webapp.dto.UserDto;
import fingerorder.webapp.dto.SignInDto;
import fingerorder.webapp.dto.SignUpDto;
import fingerorder.webapp.dto.UserEditDto;
import fingerorder.webapp.dto.UserInfoDto;
import fingerorder.webapp.dto.UserPasswordResetDto;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
	UserDto signUp(SignUpDto signUpDto);
	UserDto authenticate(SignInDto signInDto);
	UserDto getUserInfo(UserInfoDto userInfoDto);
	List<String> getRoles(SignInDto signInDto);
	UserDto editUserInfo(UserEditDto userEditDto);
	String resetPassword(UserPasswordResetDto userPasswordResetDto);
}
