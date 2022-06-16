package core.accountModel;

import core.common.IRepo;

public interface IAccountRepo extends IRepo<Account> {
	public boolean emailTaken(String email);
}
