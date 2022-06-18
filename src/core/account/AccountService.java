package core.account;

public class AccountService implements IAccountService {
	private IAccountRepo repo;
	
	public AccountService(IAccountRepo repo) {
		this.repo = repo;
	}
	
	@Override
	public Account getByCredentials(String email, String password) {
		return repo.getByCredentials(email, password);
	}

	@Override
	public Account add(Account account) {
		return repo.add(account);
	}

	@Override
	public boolean emailTaken(String email) {
		return repo.emailTaken(email);
	}

	@Override
	public Account get(int id) {
		return repo.get(id);
	}
}
