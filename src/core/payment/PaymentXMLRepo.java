package core.payment;

import java.util.List;

import core.common.DefaultXMLRepo;
import core.common.MasterXMLRepo;

public class PaymentXMLRepo extends DefaultXMLRepo<Payment> implements IPaymentRepo {

	public PaymentXMLRepo(MasterXMLRepo master) {
		super(master);
	}

	@Override
	public List<Payment> getAll() {
		return master.getPayments();
	}

}
