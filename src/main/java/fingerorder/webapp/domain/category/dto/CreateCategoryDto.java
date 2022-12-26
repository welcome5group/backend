package fingerorder.webapp.domain.category.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryDto {
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z가-힣0-9 ()]*$")
    private String name;

}