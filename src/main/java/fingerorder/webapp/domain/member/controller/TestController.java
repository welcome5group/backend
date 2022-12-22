package fingerorder.webapp.domain.member.controller;

import fingerorder.webapp.domain.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
