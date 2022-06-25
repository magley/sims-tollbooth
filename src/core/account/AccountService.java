package core.account;

import core.common.ServiceAdapter;

public class AccountService extends ServiceAdapter<Account> implements IAccountService {
	public AccountService(IAccountRepo repo) {
		super(repo);
	}

	@Override
	public Account getByCredentials(String email, String password) {
		// TODO: Is this avoidable? Is this acceptable?
		return ((IAccountRepo) repo).getByCredentials(email, password);
	}

	@Override
	public boolean emailTaken(String email) {
		// TODO: Is this avoidable? Is this acceptable?
		return ((IAccountRepo) repo).emailTaken(email);
	}
}
