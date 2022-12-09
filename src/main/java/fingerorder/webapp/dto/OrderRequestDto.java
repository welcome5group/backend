//package fingerorder.webapp.dto;
//
//import fingerorder.webapp.entity.Member;
//import fingerorder.webapp.entity.Order;
//import fingerorder.webapp.entity.Store;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//public class OrderRequestDto {
//
//    private Long memberId;
//    private Long storeId;
//    private int totalPrice;
//
//    public Order toEntity(Member member, Store store) {
//        return Order.builder()
//            .member(member)
//            .store(store)
//            .totalPrice(totalPrice)
//            .build();
//    }
//
//}
