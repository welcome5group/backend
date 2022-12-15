package fingerorder.webapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

	@GetMapping("api/store/{categoryId}/category")
	public void category(@PathVariable Long categoryId) {


	}
}
