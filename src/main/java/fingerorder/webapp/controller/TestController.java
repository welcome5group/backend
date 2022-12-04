package fingerorder.webapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @ResponseBody
    @GetMapping("/aws-v1")
    public String test() {

        return "22";
    }

}
