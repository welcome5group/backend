package fingerorder.webapp.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TokenDto {

    private String grantType;
    private String accessToken;
    private Long accessTokenTokenExpirationTime;
    private String refreshToken;
    private Long refreshTokenExpirationTime;

}
