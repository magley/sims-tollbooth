package core.accountModel;

public interface IAccountService {
	public Account getByCredentials(String email, String password);
}
