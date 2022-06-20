package core.account;

import java.util.List;

import core.common.DefaultXMLRepo;
import core.common.MasterXMLRepo;

public class AccountXMLRepo extends DefaultXMLRepo<Account> implements IAccountRepo {
	public AccountXMLRepo(MasterXMLRepo master) {
		super(master);
	}

	@Override
	public List<Account> getAll() {
		return master.getAccounts();
	}

	@Override
	public boolean emailTaken(String email) {
		return getAll().stream().anyMatch(a -> a.getEmail().equals(email));
	}

	@Override
	public Account getByCredentials(String email, String password) {
		return getAll().stream().filter(a -> a.getEmail().equals(email) && a.getPassword().equals(password)).findFirst()
				.orElse(null);
	}
}
