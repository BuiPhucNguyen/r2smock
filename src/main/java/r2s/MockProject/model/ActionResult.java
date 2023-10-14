package r2s.MockProject.model;

import lombok.Data;
import r2s.MockProject.enums.ErrorCodeEnum;

@Data
public class ActionResult {
	private ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.OK;
	private Object data;
}
