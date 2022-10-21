package whim.project.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Значение возвращаемое при ошибке")
public class ErrorResponse {

	@Schema(description = "дата ошибки")
	private String timestamp;
	@Schema(description = "возвращенный http status")
	private int status;
	@Schema(description = "описание http status")
	private String error;
	@Schema(description = "stack trace of this error")
	private String trace;
	@Schema(description = "мое сообщение об ошибке")
	private String message;
	@Schema(description = "путь по которому случилась ошибка")
	private String path;
}
