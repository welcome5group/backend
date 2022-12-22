package fingerorder.webapp.domain.store.service;

import fingerorder.webapp.domain.member.entity.Member;
import fingerorder.webapp.domain.member.exception.MemberFindException;
import fingerorder.webapp.domain.member.repository.MemberRepository;
import fingerorder.webapp.domain.store.entity.Store;
import fingerorder.webapp.domain.store.exception.StoreFindException;
import fingerorder.webapp.domain.store.repository.StoreRepository;
import fingerorder.webapp.dto.request.create.StoreCreateRequest;
import fingerorder.webapp.dto.request.update.StoreUpdateRequest;
import fingerorder.webapp.dto.response.StoreResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public StoreResponse registerStore(StoreCreateRequest storeCreateRequest) { // 가게 생성

        Store store = new Store(storeCreateRequest);
        Store savedStore = storeRepository.save(store);
        Member member = memberRepository.findById(storeCreateRequest.getMemberId())
            .orElseThrow(MemberFindException::new);
        member.addStore(savedStore);
        return savedStore.toStoreRequest(savedStore);
    }

    @Transactional
    public StoreResponse updateStore(StoreUpdateRequest storeUpdateRequest, Long storeId) {

        Store store = storeRepository.findById(storeId)
            .orElseThrow(StoreFindException::new);
        store.changeStore(storeUpdateRequest);
        return store.toStoreRequest(store);
    }

    @Transactional
    public void deleteStore(Long storeId) {

        storeRepository.deleteById(storeId);
    }

    public List<StoreResponse> findAllStores(Long memberId) { //memberId

        List<Store> stores = storeRepository.findAllByMemberId(memberId);
        return stores.stream()
            .map(s -> new StoreResponse(s.getId(), s.getName(), s.getStoreLocation())).collect(
                Collectors.toList());

    }


}
