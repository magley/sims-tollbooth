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
}
