package fingerorder.webapp.domain.member.controller;

import fingerorder.webapp.domain.member.dto.TokenResponseDto;
import fingerorder.webapp.domain.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class KakaoController {
	private final UserService userService;

	@RequestMapping("/kakao_callback")
	public String kakaoCallback(@RequestParam String type,@RequestParam String code, Model model) {
		TokenResponseDto tokenResponseDto = this.userService.kakaoSignIn(code,type);

		model.addAttribute(tokenResponseDto);
		if (type.equals("MEMBER")) {
			return "redirect:https://fingeroreder-order.netlify.app/kakao?token="+
				tokenResponseDto.getAccessToken();
		}
		return "redirect:https://fingerorder.vercel.app/login/kakaologin/"+
			tokenResponseDto.getAccessToken();
	}
}