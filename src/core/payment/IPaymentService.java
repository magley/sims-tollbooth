package core.payment;

import java.util.Date;
import java.util.List;

import core.common.IService;
import core.pricelist.entry.PricelistEntry.Currency;
import core.pricelist.entry.PricelistEntry.VehicleCategory;
import core.station.Station;

public interface IPaymentService extends IService<Payment> {
	
	double getProfitForReport(List<Station> stations, List<VehicleCategory> categories, Currency currency, Date currDate);

}
