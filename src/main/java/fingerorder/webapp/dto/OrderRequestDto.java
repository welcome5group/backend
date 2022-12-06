package fingerorder.webapp.dto;

import fingerorder.webapp.entity.Member;
import fingerorder.webapp.entity.Order;
import fingerorder.webapp.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderRequestDto {

    private Long memberId;
    private Long storeId;

    private int totalPrice;

    public Order toEntity(Member member, Store store) {
        return Order.builder()
            .member(member)
            .store(store)
            .totalPrice(totalPrice)
            .build();
    }

}
