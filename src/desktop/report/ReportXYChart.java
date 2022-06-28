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

public class ReportXYChart {

	private AppContext ctx;
	private List<Integer> xAxis; //dates
	private List<Integer> yAxis; //earnings
	public XYChart chart;
	public int totalProfit;

	@SuppressWarnings("deprecation")
	public ReportXYChart(List<Station> stations, List<VehicleCategory> categories, Currency currency, Date startDate,
			Date endDate, AppContext ctx) throws EmptyAxisException {
		this.ctx = ctx;
		this.yAxis = new ArrayList<Integer>();
		this.xAxis = new ArrayList<Integer>();
		
		Integer i = 0;
		for(Date currDate=startDate; currDate.before(endDate); currDate.setDate(currDate.getDate()+1)) {
			Integer profit = this.ctx.getPaymentService().getProfitForReport(stations, categories, currency, currDate);
			yAxis.add(profit);
			xAxis.add(i);
			totalProfit+=profit;
			i++;
		}
		
		if (xAxis.isEmpty() || yAxis.isEmpty()) {
			throw new EmptyAxisException();
		}
		
		initChart();
	}
	
	private void initChart() {
		chart = new XYChartBuilder().xAxisTitle("Serial day number").yAxisTitle("Earnings").width(700).height(500).build();
		
	    chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.GREY));
	    chart.getStyler().setPlotGridLinesColor(new Color(255, 255, 255));
	    chart.getStyler().setChartBackgroundColor(Color.WHITE);
	    chart.getStyler().setPlotGridLinesVisible(false);
	    
		XYSeries series = chart.addSeries("Earnings by day", xAxis, yAxis);
		series.setMarker(SeriesMarkers.NONE);
		series.setLineColor(XChartSeriesColors.BLUE);
	    series.setMarkerColor(Color.ORANGE);
	    series.setMarker(SeriesMarkers.CIRCLE);
	    series.setLineStyle(SeriesLines.SOLID);
	}
}
