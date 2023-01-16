package fingerorder.webapp.domain.menu.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuImgResponse {
	private String imageUrl;

	@Builder
	public MenuImgResponse(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
