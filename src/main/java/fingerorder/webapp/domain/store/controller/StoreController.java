package fingerorder.webapp.domain.store.controller;

import fingerorder.webapp.core.dto.Result;
import fingerorder.webapp.domain.store.dto.OrderDetailsRequestDto;
import fingerorder.webapp.domain.store.dto.OrderDetailsResponseDto;
import fingerorder.webapp.domain.store.dto.PaymentDatailsRequestDto;
import fingerorder.webapp.domain.store.dto.PaymentDetailsResponseDto;
import fingerorder.webapp.domain.store.dto.StoreCreateRequest;
import fingerorder.webapp.domain.store.dto.StoreUpdateResponse;
import fingerorder.webapp.domain.store.dto.StoreUpdateRequest;
import fingerorder.webapp.domain.store.service.StoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;

//    Validation 체크
//→ 매장명, 매장 위치, 타입의 양쪽 공백 지움
//→ 매장명, 매장 위치, 타입이 빈 문자열인지 체크
//→ 테이블 수를 입력했는지 체크

    //매장 생성
    @PostMapping
    public ResponseEntity<StoreUpdateResponse> registerStore(
        @Validated @RequestBody StoreCreateRequest storeCreateRequest
        , BindingResult bindingResult) {
        StoreUpdateResponse storeUpdateResponse = storeService.registerStore(storeCreateRequest);
        return ResponseEntity.ok(storeUpdateResponse);
    }

    //매장 수정
    @PutMapping("/{storeId}")
    public ResponseEntity<StoreUpdateResponse> updateStore(
        @Validated @RequestBody StoreUpdateRequest storeUpdateRequest,
        BindingResult bindingResult,
        @PathVariable("storeId") Long storeId) {
        StoreUpdateResponse storeUpdateResponse = storeService.updateStore(storeUpdateRequest, storeId);
        return ResponseEntity.ok(storeUpdateResponse);
    }

    //매장 삭제
    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> deleteStore(@PathVariable Long storeId) {
        storeService.deleteStore(storeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //사장의 모든 매장 조회
    @GetMapping
    public ResponseEntity<Result<List<StoreUpdateResponse>>> findAllStores(@RequestParam Long memberId) {
        List<StoreUpdateResponse> stores = storeService.findAllStores(memberId);
        return new ResponseEntity<>(new Result<>(stores), HttpStatus.OK);
    }

    @GetMapping("/payment-details")
    public ResponseEntity<List<PaymentDetailsResponseDto>> getPaymentDetails(@RequestBody PaymentDatailsRequestDto paymentDatailsRequestDto) {

        return new ResponseEntity<>(storeService.findSalesForMonth(paymentDatailsRequestDto), HttpStatus.OK);
    }

    @GetMapping("/order-details")
    public ResponseEntity<List<OrderDetailsResponseDto>> getOrderDetails(@RequestBody OrderDetailsRequestDto orderDetailsRequestDto) {

        return ResponseEntity.ok(storeService.findOrderDetails(orderDetailsRequestDto));
    }
}
