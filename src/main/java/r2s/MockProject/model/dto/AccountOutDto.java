package r2s.MockProject.model.dto;

import java.util.List;

import lombok.Data;
import r2s.MockProject.model.entity.AccountModel;

@Data
public class AccountOutDto {
	private List<AccountModel> accounts;
	private Integer total;
}
