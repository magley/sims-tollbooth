package desktop.report;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import core.AppContext;
import core.pricelist.entry.PricelistEntry.Currency;
import core.pricelist.entry.PricelistEntry.VehicleCategory;
import core.station.Station;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.colors.ChartColor;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class ReportXChart {

	private AppContext ctx;
	private List<Integer> xAxis; //dates
	private List<Double> yAxis; //earnings
	public XYChart chart;

	@SuppressWarnings("deprecation")
	public ReportXChart(List<Station> stations, List<VehicleCategory> categories, Currency currency, Date startDate,
			Date endDate, AppContext ctx) throws EmptyAxisException {
		this.ctx = ctx;
		this.yAxis = new ArrayList<Double>();
		this.xAxis = new ArrayList<Integer>();
		
		// ukoliko je enddate - startdate == 1 onda ispisuj sate...
		// odvojiti u posebne funkcije... buvalno ovdje samo dobaviti payment za taj dan
		
		int i = 0;
		for(Date currDate=startDate; currDate.before(endDate); currDate.setDate(currDate.getDate()+1)) {
			double profit = this.ctx.getPaymentService().getProfitForReport(stations, categories, currency, currDate);
			//if (profit == 0) continue;
			yAxis.add(profit);
			xAxis.add(i);
			i++;
			System.out.println(profit);
			System.out.println(currDate);
		}
		
		if (xAxis.isEmpty() || yAxis.isEmpty()) {
			throw new EmptyAxisException();
		}
		
		chart = new XYChartBuilder().xAxisTitle("Date").yAxisTitle("Earnings").width(600).height(400).build();
		
	    chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.GREY));
	    chart.getStyler().setPlotGridLinesColor(new Color(255, 255, 255));
	    chart.getStyler().setChartBackgroundColor(Color.WHITE);
	    chart.getStyler().setPlotGridLinesVisible(false);
	    chart.getStyler().setDatePattern("dd-MMM");
	    
		XYSeries series = chart.addSeries("Earnings by date", xAxis, yAxis);
		series.setMarker(SeriesMarkers.NONE);
		series.setLineColor(XChartSeriesColors.BLUE);
	    series.setMarkerColor(Color.ORANGE);
	    series.setMarker(SeriesMarkers.CIRCLE);
	    series.setLineStyle(SeriesLines.SOLID);
		chart.getStyler().setDatePattern("dd-MMM");
	}
}
