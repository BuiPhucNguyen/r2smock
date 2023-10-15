package r2s.MockProject.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import r2s.MockProject.entity.Account;
import r2s.MockProject.entity.Role;
import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.LoginDto;
import r2s.MockProject.model.dto.SignUpDto;
import r2s.MockProject.model.entity.AccountModel;
import r2s.MockProject.repository.AccountRepository;
import r2s.MockProject.repository.RoleRepository;
import r2s.MockProject.service.LoginService;
import r2s.MockProject.utils.JwtUtils;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CustomUserDetailServiceImpl customUserDetailServiceImpl;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public ActionResult login(LoginDto login) {
		ActionResult result = new ActionResult();
		UserDetails userDetails = customUserDetailServiceImpl.loadUserByUsername(login.getUsername());

		if (userDetails == null) {
			result.setErrorCodeEnum(ErrorCodeEnum.UNAUTHORIZED);
			return result;
		} else {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(),
					login.getPassword(), userDetails.getAuthorities()));

			String token = JwtUtils.generateToken(userDetails.getUsername());

			result.setData(token);
			return result;
		}
	}

	@Override
	public ActionResult signup(SignUpDto signup) {
		ActionResult result = new ActionResult();
		
		if (accountRepository.findByUsername(signup.getUsername())!=null) {
			result.setErrorCodeEnum(ErrorCodeEnum.EXISTED_USERNAME_ACCOUNT);
			return result;
		}
		
		if (accountRepository.findByEmail(signup.getEmail())!=null) {
			result.setErrorCodeEnum(ErrorCodeEnum.EXISTED_EMAIL_ACCOUNT);
			return result;
		}
		
		Account account = new Account();
		
		account.setFirstName(signup.getFirstName());
		account.setLastName(signup.getLastName());
		account.setEmail(signup.getEmail());
		account.setUserName(signup.getUsername());
		account.setPassword(passwordEncoder.encode(signup.getPassword()));
		
		account.setStatus(true);
		account.setCreatedDate(new Date());
		
		Role role = roleRepository.getRoleByName("USER");
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		account.setRoles(roles);
		
		Account accountResult = accountRepository.save(account);
		
		if (accountResult==null) {
			result.setErrorCodeEnum(ErrorCodeEnum.INVALID_CREATE);
			return result;
		}
		
		result.setData(AccountModel.transform(accountResult));
		return result;
	}
}
