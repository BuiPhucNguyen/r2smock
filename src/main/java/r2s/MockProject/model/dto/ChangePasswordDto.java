package r2s.MockProject.model.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
	private String oldPassword;
	private String newPassword; 
}
