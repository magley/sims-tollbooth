package core.payment;

import core.common.ServiceAdapter;

public class PaymentService extends ServiceAdapter<Payment> implements IPaymentService {

	public PaymentService(IPaymentRepo repo) {
		super(repo);
	}

}
