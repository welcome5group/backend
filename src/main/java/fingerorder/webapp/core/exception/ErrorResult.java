package fingerorder.webapp.core.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {

    private String errCode;
    private String message;


}
