package fingerorder.webapp.service;

import fingerorder.webapp.dto.UserDto;
import fingerorder.webapp.dto.SignInDto;
import fingerorder.webapp.dto.SignUpDto;
import fingerorder.webapp.dto.UserEditDto;
import fingerorder.webapp.dto.UserInfoDto;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
	UserDto signUp(SignUpDto signUpParam);
	UserDto authenticate(SignInDto signInParam);
	UserDto getUserInfo(UserInfoDto userParam);
	List<String> getRoles(SignInDto signInParam);
	UserDto editUserInfo(UserEditDto userEditParam);
}
