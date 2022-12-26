package fingerorder.webapp.domain.order.controller;

import fingerorder.webapp.domain.order.dto.GetIncompOrdersResponse;
import fingerorder.webapp.domain.order.dto.SaveOrderRequest;
import fingerorder.webapp.domain.order.entity.Order;
import fingerorder.webapp.domain.order.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<?> save(@RequestBody final SaveOrderRequest saveOrderRequest) {
        return orderService.save(saveOrderRequest);
    }

    @GetMapping("/incomp-orders/{storeId}")
    public ResponseEntity<List<GetIncompOrdersResponse>> getIncompOrders(@PathVariable Long storeId) {
        return ResponseEntity.ok(orderService.getIncompOrders(storeId));
    }
}