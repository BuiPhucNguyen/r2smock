package r2s.MockProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.ResponseBuild;
import r2s.MockProject.model.ResponseModel;
import r2s.MockProject.model.dto.AccountInDto;
import r2s.MockProject.service.AccountService;


@RestController
@RequestMapping("/accounts")
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ResponseBuild responseBuild;
	
	@GetMapping("/all")
	public ResponseModel getAccountPaging(@Param(value = "page") Integer page, @Param(value = "size") Integer size) {
		ActionResult result = null;
		try {
			result = accountService.getAccountsNotAdmin(page,size);
		} catch (Exception e) {
			result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		return responseBuild.build(result); 
	}
	
	@GetMapping("/{accountId}")
	public ResponseModel getAccountById(@PathVariable Integer accountId) {
		ActionResult result = null;
		try {
			result = accountService.getAccountById(accountId);
		} catch (Exception e) {
			result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		return responseBuild.build(result); 
	}
	
	@GetMapping("/current")
	public ResponseModel getCurrentAccount() {
		ActionResult result = null;
		try {
			result = accountService.getCurrentAccount();
		} catch (Exception e) {
			result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		return responseBuild.build(result); 
	}
	
	@PutMapping("/")
	public ResponseModel updateAccount(@RequestBody AccountInDto account) {
		ActionResult result = null;
		try {
			result = accountService.updatetAccount(account);
		} catch (Exception e) {
			result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		return responseBuild.build(result); 
	}
	
	@PutMapping("/status/{accountId}")
	public ResponseModel updateAccountStatus(@PathVariable Integer accountId) {
		ActionResult result = null;
		try {
			result = accountService.ableAccount(accountId);
		} catch (Exception e) {
			result.setErrorCodeEnum(ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		return responseBuild.build(result); 
	}
}
