package core.account;

import core.account.exception.AccountNotFoundException;
import core.common.FieldEmptyException;

public class AccountController {
	private IAccountService service;
	
	public AccountController(IAccountService service) {
		this.service = service;
	}
	
	public Account login(String email, String password) throws FieldEmptyException, AccountNotFoundException {
		if (email.isBlank() || password.isBlank()) {
			throw new FieldEmptyException();
		}
		
		Account acc = service.getByCredentials(email, password);
		
		if (acc == null) {
			throw new AccountNotFoundException();
		}
		
		return acc;
	}
}
