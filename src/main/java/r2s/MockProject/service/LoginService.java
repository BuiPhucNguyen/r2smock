package r2s.MockProject.service;

import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.LoginDto;
import r2s.MockProject.model.dto.SignUpDto;

public interface LoginService {
	ActionResult login(LoginDto login);
	ActionResult signup(SignUpDto signup);
}
