package fingerorder.webapp.domain.member.controller;

import fingerorder.webapp.domain.member.dto.TokenDto;
import fingerorder.webapp.domain.member.service.UserService;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final UserService userService;
    @ResponseBody
    @GetMapping("/aws-v1")
    public String test() {

        return "22";
    }

    @GetMapping("/api/auth/kakao/sign-in")
    public String kakaoLogin() {
        StringBuffer loginUrl = new StringBuffer();
        loginUrl.append("https://kauth.kakao.com/oauth/authorize?client_id=");
        loginUrl.append("43b7585079d42f271bc7c481ffca8f03");
        loginUrl.append("&redirect_uri=");
        loginUrl.append("http://localhost:8080/kakao_callback");
        loginUrl.append("&response_type=code");

        return "redirect:"+loginUrl.toString();
    }

    @RequestMapping("/kakao_callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam String code, HttpSession session) {
        TokenDto tokenDto = userService.kakaoSignIn(code);
        return ResponseEntity.ok(tokenDto);
    }
}
