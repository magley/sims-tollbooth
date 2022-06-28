package core.payment;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import core.common.ServiceAdapter;
import core.pricelist.entry.PricelistEntry.Currency;
import core.pricelist.entry.PricelistEntry.VehicleCategory;
import core.station.Station;

public class PaymentService extends ServiceAdapter<Payment> implements IPaymentService {

	public PaymentService(IPaymentRepo repo) {
		super(repo);
	}

	private List<Payment> getByDayAndCurrency(Date date, Currency currency) {
		List<Payment> payments = new ArrayList<Payment>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		for (Payment p: this.repo.getAll()) {
			if (sdf.format(date).equals(p.getTime().format(formatter)) && p.getPricelistEntry().getCurrency().equals(currency)) {
				payments.add(p);
			}
		}
		 return payments;
	}

	private Integer calculateProfit(List<Payment> payments) {
		Integer profit = 0;
		for (Payment p: payments) {
			profit += p.getPricelistEntry().getPrice();
		}
		return profit;
	}

	private List<Payment> getByVehicleCategories(List<Payment> payments, List<VehicleCategory> categories) {
		List<Payment> ps = new ArrayList<Payment>();
		for (Payment p: payments) {
			if (categories.contains(p.getPricelistEntry().getCategory())) {
				ps.add(p);
			}
		}
		return ps;
	}

	private List<Payment> getByStations(List<Payment> payments, List<Station> stations) {
		List<Payment> ps = new ArrayList<Payment>();
		for (Payment p: payments) {
			if (stations.contains(p.getPricelistEntry().getExit())) {
				ps.add(p);
			}
		}
		return ps;
	}

	public Integer getProfitForReport(List<Station> stations, List<VehicleCategory> categories, Currency currency,
			Date currDate) {
		List<Payment> payments = getByDayAndCurrency(currDate, currency);
		payments = getByVehicleCategories(payments, categories);
		payments = getByStations(payments, stations);
		Integer profit = calculateProfit(payments);
		return profit;
	}

}
