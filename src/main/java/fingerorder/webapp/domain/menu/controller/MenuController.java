package fingerorder.webapp.domain.menu.controller;

import fingerorder.webapp.domain.menu.dto.MenuCreateRequest;
import fingerorder.webapp.domain.menu.dto.MenuResponse;
import fingerorder.webapp.domain.menu.dto.MenuUpdateRequest;
import fingerorder.webapp.core.dto.Result;
import fingerorder.webapp.domain.menu.dto.menuquerydto.MenuAndCategory;
import fingerorder.webapp.domain.menu.service.MenuQueryService;
import fingerorder.webapp.domain.menu.service.MenuService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store/menu")
public class MenuController {

    private final MenuService menuService;
    private final MenuQueryService menuQueryService;

    @PostMapping //메뉴 추가 id : menuId
    public ResponseEntity<MenuResponse> createMenu(
        @RequestBody MenuCreateRequest menuCreateRequest) {
        MenuResponse menuResponse = menuService.registerMenu(menuCreateRequest);
        return new ResponseEntity<>(menuResponse, HttpStatus.OK);
    }

    @PutMapping  //메뉴 수정 id : menuId
    public ResponseEntity<MenuResponse> updateMenu(
        @RequestBody MenuUpdateRequest menuUpdateRequest) {
        MenuResponse menuResponse = menuService.updateMenu(menuUpdateRequest);
        return new ResponseEntity<>(menuResponse, HttpStatus.OK);
    }

    //메뉴 삭제
    @DeleteMapping  //메뉴 수정 id : menuId
    public ResponseEntity<?> deleteMenu(@RequestParam Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.ok("200");
    }

    //메뉴와 카테고리 넘겨주기 이제 해야함
    @GetMapping
    public Result<?> findMenuAndCategory(@RequestParam Long storeId) {
        List<MenuAndCategory> menuAndCategory = menuQueryService.findMenuAndCategory(storeId);
        return new Result<>(menuAndCategory);
    }
}
