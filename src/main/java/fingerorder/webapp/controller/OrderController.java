package fingerorder.webapp.controller;

import fingerorder.webapp.dto.OrderRequestDto;
import fingerorder.webapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/guest/store")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public Long save(@RequestBody final OrderRequestDto params) {
        return orderService.save(params);
    }
}
