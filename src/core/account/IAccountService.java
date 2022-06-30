package core.account;

import core.common.IService;

public interface IAccountService extends IService<Account> {
	public Account getByCredentials(String email, String password);

	public boolean emailTaken(String string);
}
