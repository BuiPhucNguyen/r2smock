package r2s.MockProject.service;

import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.AccountInDto;

public interface AccountService {
	ActionResult getAccountsNotAdmin(Integer page, Integer size); //admin
	
	ActionResult getAccountById(Integer id); //admin
	
	ActionResult getCurrentAccount(); //admin,user
	
	ActionResult updatetAccount(AccountInDto newAccount); //user
	
	ActionResult ableAccount(Integer id); //admin
}
