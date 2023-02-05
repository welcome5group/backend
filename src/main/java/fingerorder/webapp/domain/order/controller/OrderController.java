package fingerorder.webapp.domain.order.controller;

import fingerorder.webapp.domain.order.dto.IncompOrderListResponse;
import fingerorder.webapp.domain.order.dto.OrderAddRequest;
import fingerorder.webapp.domain.order.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/guest/store/order")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> orderAdd(@RequestBody final OrderAddRequest orderAddRequest) {
        return ResponseEntity.ok(orderService.addOrder(orderAddRequest));
    }

    @GetMapping("/store/{storeId}/orders")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<List<IncompOrderListResponse>> incompOrderList(
        @PathVariable Long storeId) {
        return ResponseEntity.ok(orderService.findIncompOrders(storeId));
    }

    @PutMapping("/store/order/{orderId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> orderStatusModify(@PathVariable Long orderId) {
        orderService.modifyOrderStatus(orderId);
        return ResponseEntity.ok(orderService.modifyOrderStatus(orderId));
    }

    @GetMapping("/user/orders")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> orderList(@RequestParam Long memberId) {
        return ResponseEntity.ok(orderService.findOrders(memberId));
    }
}

