package r2s.MockProject.service;

import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.AccountInDto;

public interface AccountService {
	ActionResult getAccountsNotAdmin(Integer page, Integer size);
	
	ActionResult getAccountById(Integer id);
	
	ActionResult updatetAccount(Integer id, AccountInDto newAccount);
	
	ActionResult ableAccount(Integer id);
}
