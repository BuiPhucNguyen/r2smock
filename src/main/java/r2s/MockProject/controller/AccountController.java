package r2s.MockProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.ResponseBuild;
import r2s.MockProject.model.ResponseModel;
import r2s.MockProject.service.AccountService;


@RestController
@RequestMapping("/accounts")
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ResponseBuild responseBuild;
	
	@GetMapping("/")
	public ResponseModel getAccountPaging(@Param(value = "page") Integer page, @Param(value = "size") Integer size) {
		ActionResult result = null;
		try {
			result = accountService.getAccountsNotAdmin(page,size);
		} catch (Exception e) {
			result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		return responseBuild.build(result); 
	}
}