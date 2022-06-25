package core.account;

import core.common.ServiceAdapter;

public class AccountService extends ServiceAdapter<Account> implements IAccountService {
	public AccountService(IAccountRepo repo) {
		super(repo);
	}

	@Override
	public Account getByCredentials(String email, String password) {
		return repo.get(a -> a.getEmail().equals(email) && a.getPassword().equals(password));
	}

	@Override
	public boolean emailTaken(String email) {
		return repo.get(a -> a.getEmail().equals(email)) != null;
	}
}
