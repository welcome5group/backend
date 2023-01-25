package fingerorder.webapp.domain.order.controller;

import fingerorder.webapp.domain.order.dto.GetInCompOrdersResponse;
import fingerorder.webapp.domain.order.dto.SaveOrderRequest;
import fingerorder.webapp.domain.order.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> save(@RequestBody final SaveOrderRequest saveOrderRequest) {
        return orderService.save(saveOrderRequest);
    }

    @GetMapping("/store/{storeId}/orders")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<List<GetInCompOrdersResponse>> getIncompOrders(@PathVariable Long storeId) {
        return ResponseEntity.ok(orderService.getInCompOrders(storeId));
    }

    @PutMapping("/store/order/{orderId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> editOrderStatus(@PathVariable Long orderId) {
        orderService.editOrderStatus(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/orders")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getOrderList(@RequestParam Long memberId) {
        return ResponseEntity.ok(orderService.getOrderList(memberId));
    }
}

