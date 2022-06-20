package core.account;

import core.common.IRepo;

public interface IAccountRepo extends IRepo<Account> {
	public boolean emailTaken(String email);

	public Account getByCredentials(String email, String password);
}
