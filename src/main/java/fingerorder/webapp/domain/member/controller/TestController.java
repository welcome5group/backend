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


}
