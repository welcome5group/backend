package fingerorder.webapp.domain.menu.controller;

import fingerorder.webapp.core.dto.Result;
import fingerorder.webapp.domain.menu.dto.MenuCreateRequest;
import fingerorder.webapp.domain.menu.dto.MenuResponse;
import fingerorder.webapp.domain.menu.dto.MenuUpdateRequest;
import fingerorder.webapp.domain.menu.dto.menuquerydto.MenuAndCategory;
import fingerorder.webapp.domain.menu.service.MenuQueryService;
import fingerorder.webapp.domain.menu.service.MenuService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/store/{storeId}/menu")
@Slf4j
public class MenuController {

    private final MenuService menuService;
    private final MenuQueryService menuQueryService;

    // → 정규식(영소문자, 영대문자, 한글, 숫자, 공백, 소괄호)

    //메뉴 추가
    @PostMapping
    public ResponseEntity<MenuResponse> createMenu(
        @Validated @RequestBody MenuCreateRequest menuCreateRequest, BindingResult bindingResult,
        @PathVariable("storeId") Long storeId) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
        }

        MenuResponse menuResponse = menuService.registerMenu(menuCreateRequest, storeId);
        return new ResponseEntity<>(menuResponse, HttpStatus.OK);
    }

    //메뉴 수정t
    @PutMapping //메뉴 수정에는 storeId가 필요가 없다(o)
    public ResponseEntity<MenuResponse> updateMenu(
        @Validated @RequestBody MenuUpdateRequest menuUpdateRequest, BindingResult bindingResult,
        @PathVariable("storeId") Long storeId) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
        }

        MenuResponse menuResponse = menuService.updateMenu(menuUpdateRequest);
        return new ResponseEntity<>(menuResponse, HttpStatus.OK);
    }

    //메뉴 삭제
    @DeleteMapping //메뉴 삭제에는 storeId가 필요가 없다(o)
    //질문 : 메뉴를 삭제하면 연관관계 편의 메서드로 넣어준 store 상의 menu도 사라질까?
    public ResponseEntity<?> deleteMenu(@RequestParam Long menuId,
        @PathVariable("storeId") Long storeId) {
        menuService.deleteMenu(menuId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //메뉴와 카테고리 넘겨주기
    @GetMapping
    public Result<?> findMenuAndCategory(@PathVariable("storeId") Long storeId) {
        List<MenuAndCategory> menuAndCategory = menuQueryService.findMenuAndCategory(storeId);
        return new Result<>(menuAndCategory);
    }
}
