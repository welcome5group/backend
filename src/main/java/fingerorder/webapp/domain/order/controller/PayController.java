package fingerorder.webapp.domain.order.controller;

import fingerorder.webapp.domain.order.dto.FindPaysResponse;
import fingerorder.webapp.domain.order.service.PayService;
import java.util.List;
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

    @GetMapping("/{memberId}/pays")
    public List<FindPaysResponse> getList(@PathVariable("memberId") Long memberId) {
        return payService.findPayList(memberId);
    }

}
