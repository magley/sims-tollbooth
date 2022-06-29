package core.payment;

public class PaymentController {

	private IPaymentService service;

	public PaymentController(IPaymentService service) {
		super();
		this.service = service;
	}
}
