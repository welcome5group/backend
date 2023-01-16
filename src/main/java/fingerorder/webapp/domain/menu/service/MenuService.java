package fingerorder.webapp.domain.menu.service;

import fingerorder.webapp.core.s3.AwsS3Service;
import fingerorder.webapp.domain.category.entity.Category;
import fingerorder.webapp.domain.category.exception.CategoryNotFoundException;
import fingerorder.webapp.domain.category.repository.CategoryRepository;
import fingerorder.webapp.domain.menu.dto.MenuCreateRequest;
import fingerorder.webapp.domain.menu.dto.MenuResponse;
import fingerorder.webapp.domain.menu.dto.MenuUpdateRequest;
import fingerorder.webapp.domain.menu.dto.MenuImgResponse;
import fingerorder.webapp.domain.menu.entity.Menu;
import fingerorder.webapp.domain.menu.exception.MenuNotFindException;
import fingerorder.webapp.domain.menu.repository.MenuRepository;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.store.exception.StoreNotFindException;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    private final AwsS3Service awsS3Service;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public MenuResponse registerMenu(MenuCreateRequest menuCreateRequest,
        Long storeId) { // 매장 내의 메뉴 등록
        Category category = categoryRepository.findByName(
            menuCreateRequest.getCategoryName()).orElseThrow(CategoryNotFoundException::new);
        Store store = storeRepository.findById(storeId).orElseThrow(
            StoreNotFindException::new);
        Menu menu = new Menu(menuCreateRequest, category);
        Menu savedMenu = menuRepository.save(menu);
        savedMenu.addStore(store);
        savedMenu.addCategory(category);
        return savedMenu.toMenuResponse(savedMenu);

    }

    @Transactional
    public MenuResponse updateMenu(MenuUpdateRequest menuUpdateRequest) { // 매장 내의 메뉴 수정

        Menu menu = menuRepository.findById(menuUpdateRequest.getMenuId()).orElseThrow(
            MenuNotFindException::new);
        Category category = categoryRepository.findByName(
            menuUpdateRequest.getCategoryName()).orElseThrow(CategoryNotFoundException::new);
        Menu updatedMenu = menu.updateMenu(menuUpdateRequest, category);
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
        Menu menu = menuRepository.findById(menuId).orElseThrow(MenuNotFindException::new);
        menu.editStatus();
    }

    @Transactional
    public MenuImgResponse updateMenuImg(Long menuId, MultipartFile multipartFile) {

        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
            throw new RuntimeException("id 에 해당하는 menu 가 존재하지 않습니다.");
        });

        String imageUrl = "";

        try {
            imageUrl = awsS3Service.uploadFile("menus", multipartFile);
        } catch (Exception e) {
            throw new RuntimeException("이미지 파일이 정상적으로 업로드되지 않았습니다.");
        }

        menu.editProfileImg(imageUrl);

        return MenuImgResponse.builder().imageUrl(imageUrl).build();

    }
}
