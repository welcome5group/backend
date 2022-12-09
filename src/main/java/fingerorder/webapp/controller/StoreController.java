package fingerorder.webapp.controller;

import fingerorder.webapp.dto.Result;
import fingerorder.webapp.dto.StoreCreateRequest;
import fingerorder.webapp.dto.StoreResponse;
import fingerorder.webapp.dto.StoreUpdateRequest;
import fingerorder.webapp.service.StoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/new")//가게 생성 id : storeId
    public ResponseEntity<StoreResponse> registerStore(
        @RequestBody StoreCreateRequest storeCreateRequest) {
        StoreResponse storeResponse = storeService.registerStore(storeCreateRequest);
        return ResponseEntity.ok(storeResponse);

    }

    //가게 수정 -> url 변경하기 가게 생성 url 과 동일함
    @PostMapping //id : storeId
    public ResponseEntity<StoreResponse> updateStore(
        @RequestBody StoreUpdateRequest storeUpdateRequest) {
        StoreResponse storeResponse = storeService.updateStore(storeUpdateRequest);
        return ResponseEntity.ok(storeResponse);
    }

    @DeleteMapping //가게 삭제
    public ResponseEntity<?> deleteStore(@RequestParam Long storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.ok("200");
    }

    @GetMapping //id : memberId, 사장의 모든가게 조회
    public ResponseEntity<Result<List<StoreResponse>>> findAllStores(@RequestParam Long memberId) {
        List<StoreResponse> stores = storeService.findAllStores(memberId);
        return ResponseEntity.ok(new Result<>(stores));
    }
}
