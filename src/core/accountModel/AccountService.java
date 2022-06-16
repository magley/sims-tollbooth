package core.accountModel;

public class AccountService implements IAccountService {
	private IAccountRepo repo;
	
	public AccountService(IAccountRepo repo) {
		this.repo = repo;
	}
	
	@Override
	public Account getByCredentials(String email, String password) {
		return repo.getByCredentials(email, password);
	}
}
