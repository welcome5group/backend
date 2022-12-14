package fingerorder.webapp.domain.store.controller;

import fingerorder.webapp.core.dto.Result;
import fingerorder.webapp.domain.store.dto.OrderDetailsRequestDto;
import fingerorder.webapp.domain.store.dto.OrderDetailsResponseDto;
import fingerorder.webapp.domain.store.dto.PaymentDatailsRequestDto;
import fingerorder.webapp.domain.store.dto.PaymentDetailsResponseDto;
import fingerorder.webapp.domain.store.dto.StoreCreateRequest;
import fingerorder.webapp.domain.store.dto.StoreCreateResponse;
import fingerorder.webapp.domain.store.dto.StoreLookUpResponse;
import fingerorder.webapp.domain.store.dto.StoreUpdateRequest;
import fingerorder.webapp.domain.store.dto.StoreUpdateResponse;
import fingerorder.webapp.domain.store.service.StoreService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
//    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<StoreCreateResponse> registerStore(
        @Validated @RequestBody StoreCreateRequest storeCreateRequest) {

//        if(bindingResult.hasErrors()) {
//            List<ObjectError> allErrors = bindingResult.getAllErrors();
//            for (ObjectError allError : allErrors) {
//                System.out.println(allError.getDefaultMessage());
//            }
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
        StoreCreateResponse storeCreateResponse = storeService.registerStore(storeCreateRequest);
        return ResponseEntity.ok(storeCreateResponse);
    }

    //매장 수정
    @PutMapping("/{storeId}")
//    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<StoreUpdateResponse> updateStore(
        @Validated @RequestBody StoreUpdateRequest storeUpdateRequest,
        BindingResult bindingResult,
        @PathVariable("storeId") Long storeId) {
        StoreUpdateResponse storeUpdateResponse = storeService.updateStore(storeUpdateRequest, storeId);
        return ResponseEntity.ok(storeUpdateResponse);
    }

    //매장 삭제
    @DeleteMapping("/{storeId}")
//    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<?> deleteStore(@PathVariable Long storeId) {
        storeService.deleteStore(storeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //사장의 모든 매장 조회
    @GetMapping
//    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<Result<List<StoreLookUpResponse>>> findAllStores(@RequestParam Long memberId) {
        List<StoreLookUpResponse> allStores = storeService.findAllStores(memberId);
        return new ResponseEntity<>(new Result<>(allStores), HttpStatus.OK);
    }

    @GetMapping("/payment-details")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<List<PaymentDetailsResponseDto>> getPaymentDetails(
        @RequestParam Long storeId,
        @RequestParam int year,
        @RequestParam int month
    ) {

        PaymentDatailsRequestDto paymentDatailsRequestDto = new PaymentDatailsRequestDto(storeId, year, month);
        return ResponseEntity.ok(storeService.findPaymentsForMonth(paymentDatailsRequestDto));

    }

    @GetMapping("/order-details")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<List<OrderDetailsResponseDto>> getOrderDetails(
        @RequestParam Long storeId,
        @RequestParam String startDate,
        @RequestParam String endDate
    ) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        OrderDetailsRequestDto orderDetailsRequestDto = new OrderDetailsRequestDto(
            storeId, LocalDateTime.parse(startDate, formatter), LocalDateTime.parse(endDate, formatter));

        return ResponseEntity.ok(storeService.findOrderDetails(orderDetailsRequestDto));

    }
}
