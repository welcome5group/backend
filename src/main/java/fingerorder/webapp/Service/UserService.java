package fingerorder.webapp.Service;

import fingerorder.webapp.dto.UserDto;
import fingerorder.webapp.parameter.SignInParam;
import fingerorder.webapp.parameter.SignUpParam;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
	UserDto signUp(SignUpParam signUpParam);
	UserDto authenticate(SignInParam signInParam);

	List<String> getRoles(SignInParam signInParam);
}
