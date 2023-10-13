package r2s.MockProject.enums;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public enum ErrorCodeEnum {
	OK(200, 200, "OK"),
	NO_CONTENT(204, 20401, "Not have data"),
	BAD_REQUEST(400, 40000, "Bad request"),
	INVALID_ENTITY(400, 40001, "Entity not exist"),
	INVALID_CREATE(400, 40002, "Create not success"),
	INVALID_DELETE(400, 40004, "Delete not success, entity is used"),
	INVALID_UPDATE(400, 40003, "Update not success"),
	INVALID_ENTITY_ACTIVE(400, 40005, "Entity not active"),
    INTERNAL_SERVER_ERROR(500, 50000, "Internal Server Error"),
    FORBIDDEN(403, 403, "Access is denied"),
    UNAUTHORIZED(401, 401, "Not authenticated"),;

	private static Map<Integer, ErrorCodeEnum> map = new HashMap<>();

	static {
		for (ErrorCodeEnum returnCode : ErrorCodeEnum.values()) {
			map.put(returnCode.code, returnCode);
		}
	}

	private int status;
	private int code;
	private String message;

	private ErrorCodeEnum(int status, int code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public static ErrorCodeEnum valueOf(int code) {
		return map.get(code);
	}

	public int getStatus() {
		return status;
	}

	public HttpStatus getHttpStatus() {
		return HttpStatus.valueOf(status);
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}