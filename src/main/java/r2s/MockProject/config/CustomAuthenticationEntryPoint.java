package r2s.MockProject.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.model.ResponseModel;
import r2s.MockProject.utils.JsonHandler;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		ResponseModel responseModel = new ResponseModel(ErrorCodeEnum.FORBIDDEN, null);

		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(403);
		response.getWriter().write(JsonHandler.getJsonString(responseModel));
	}

}
