package core.accountModel;

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
		for (Account a : getAll()) {
			if (a.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
}
