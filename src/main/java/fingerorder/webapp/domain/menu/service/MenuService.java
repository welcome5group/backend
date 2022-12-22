package fingerorder.webapp.domain.menu.service;

import fingerorder.webapp.domain.menu.dto.MenuCreateRequest;
import fingerorder.webapp.domain.menu.dto.MenuResponse;
import fingerorder.webapp.domain.menu.dto.MenuUpdateRequest;
import fingerorder.webapp.domain.menu.entity.Menu;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.menu.repository.MenuRepository;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public MenuResponse registerMenu(MenuCreateRequest menuCreateRequest) { // 매장 내의 메뉴 등록
        Menu menu = new Menu(menuCreateRequest);
        Store store = storeRepository.findById(menuCreateRequest.getStoreId()).orElseThrow(()
            -> new RuntimeException("존재하는 가게가 없습니다."));
        Menu savedMenu = menuRepository.save(menu);
        store.addMenu(savedMenu);
        savedMenu.changeStore(store);
        return savedMenu.toMenuResponse(savedMenu);
    }

    @Transactional
    public MenuResponse updateMenu(MenuUpdateRequest menuUpdateRequest) { // 매장 내의 메뉴 수정
        Menu menu = menuRepository.findById(menuUpdateRequest.getMenuId()).orElseThrow(()
            -> new RuntimeException("존재하지 않는 매장입니다."));
        Menu updatedMenu = menu.updateMenu(menuUpdateRequest);
        return menu.toMenuResponse(updatedMenu);
    }

    @Transactional
    public void deleteMenu(Long menuId) { //매장 내의 메뉴 삭제
        menuRepository.deleteById(menuId);
    }

//    public Page<Menu> selectMenuWithCategory(PageRequest pageRequest, Category category) { //매장 내의 메뉴 조회(카테고리별 조회)
//        return menuRepository.findMenusByCategory(pageRequest, category);
//    }

//    public Page<Menu> selectMenu(PageRequest pageRequest) {
//        return menuRepository.findAll(pageRequest);
//    }

    @Transactional
    public void menuDisable(Long menuId) { //메뉴 품절로 상태 변경하기
        Menu menu = menuRepository.findById(menuId).orElseThrow(()
            -> new RuntimeException("존재하는 메뉴가 없습니다."));
        menu.changeStatus();

    }


}
