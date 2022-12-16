package fingerorder.webapp.controller;

import fingerorder.webapp.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/guest/store")
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;

    @GetMapping("/{memberId}/pay-list")
    public void getList(@PathVariable("memberId") Long memberId) {
        payService.findPayList(memberId);
    }

}
