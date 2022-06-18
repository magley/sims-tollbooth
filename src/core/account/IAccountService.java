package core.account;

public interface IAccountService {
	public Account getByCredentials(String email, String password);
	public Account add(Account account);
	public boolean emailTaken(String string);
	public Account get(int id);
}
