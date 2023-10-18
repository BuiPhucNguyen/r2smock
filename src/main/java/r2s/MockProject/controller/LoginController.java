package r2s.MockProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.ResponseBuild;
import r2s.MockProject.model.ResponseModel;
import r2s.MockProject.model.dto.ChangePasswordDto;
import r2s.MockProject.model.dto.LoginDto;
import r2s.MockProject.model.dto.SignUpDto;
import r2s.MockProject.service.LoginService;

@RestController
@RequestMapping("/auths")
public class LoginController {
	@Autowired
	private LoginService loginService;

	@Autowired
	private ResponseBuild responseBuild;

	@PostMapping("/login")
	public ResponseModel login(@RequestBody LoginDto login) {
		ActionResult result = null;
		try {
			result = loginService.login(login);
		} catch (Exception e) {
			result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		return responseBuild.build(result);
	}

	@PostMapping("/signup")
	public ResponseModel signUp(@RequestBody SignUpDto signup) {
		ActionResult result = null;
		try {
			result = loginService.signup(signup);
		} catch (Exception e) {
			result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		return responseBuild.build(result);
	}
	
	@PutMapping("/change_password")
	public ResponseModel changePassword(@RequestBody ChangePasswordDto changePassword) {
		ActionResult result = null;
		try {
			result = loginService.changePassword(changePassword);
		} catch (Exception e) {
			result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		return responseBuild.build(result);
	}
}
