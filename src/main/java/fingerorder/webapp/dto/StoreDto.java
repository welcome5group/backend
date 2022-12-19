package fingerorder.webapp.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreDto {

    private Long id;

    private String name;

    private int tableCount;

    private String storeLocation;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
