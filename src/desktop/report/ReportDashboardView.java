package desktop.report;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import core.AppContext;
import core.common.FieldEmptyException;
import core.pricelist.entry.PricelistEntry;
import core.station.Station;
import core.station.Station.Type;
import desktop.ITabbedPanel;
import net.miginfocom.swing.MigLayout;
import org.knowm.xchart.SwingWrapper;

public class ReportDashboardView extends JPanel implements ITabbedPanel {

	private static final long serialVersionUID = 4893604555701933671L;

	private AppContext ctx;
	private Station station;
	private Date startDate;
	private Date endDate;

	private JComboBox<String> cbStation;
	private JComboBox<PricelistEntry.VehicleCategory> cbVehicleCategory;
	private JComboBox<PricelistEntry.Currency> cbCurrency;
	private JTextField txtStartDate;
	private JTextField txtEndDate;
	private JTextField txtProfit;
	private JButton btnConfirm;

	public ReportDashboardView(AppContext ctx) {
		this(ctx, null);
	}

	public ReportDashboardView(AppContext ctx, Station station) {

		this.ctx = ctx;
		this.station = station;

		initGUI();
	}

	private void initGUI() {
		setLayout(new MigLayout("", "[grow]", "[][][][]"));

		JLabel lblStation = new JLabel("Station:");
		add(lblStation, "flowx,cell 0 0");
		List<String> stations = new ArrayList<String>();
		for (Station s : ctx.getStationService().getByType(Type.EXIT)) {
			stations.add(s.toString());
		}
		stations.add("ALL");
		cbStation = new JComboBox<String>(stations.toArray(new String[0]));
		add(cbStation, "cell 0 0,growx");
		if (station != null) {
			cbStation.setSelectedItem(station.toString());
			cbStation.setEnabled(false);
		}

		JLabel lblSelectCategory = new JLabel("Select vehicle category:");
		add(lblSelectCategory, "flowx,cell 0 1");

		cbVehicleCategory = new JComboBox<PricelistEntry.VehicleCategory>(PricelistEntry.VehicleCategory.values());
		add(cbVehicleCategory, "cell 0 1,growx");

		JLabel lblCurrency = new JLabel("Select currency:");
		add(lblCurrency, "flowx,cell 0 2");

		cbCurrency = new JComboBox<PricelistEntry.Currency>(PricelistEntry.Currency.values());
		cbCurrency.setSelectedItem(PricelistEntry.Currency.RSD);
		add(cbCurrency, "cell 0 2,growx");

		JLabel lblStartDate = new JLabel("From date (format dd.MM.yyyy):");
		add(lblStartDate, "flowx,cell 0 3");
		txtStartDate = new JTextField();
		add(txtStartDate, "cell 0 3,growx");

		JLabel lblEndDate = new JLabel("To date (format dd.MM.yyyy):");
		add(lblEndDate, "flowx,cell 0 4");
		txtEndDate = new JTextField();
		add(txtEndDate, "cell 0 4,growx");

		JLabel lblProfit = new JLabel("Total profit:");
		add(lblProfit, "flowx,cell 0 5");

		txtProfit = new JTextField();
		txtProfit.setEnabled(false);
		add(txtProfit, "cell 0 5,growx");
		txtProfit.setColumns(10);

		btnConfirm = new JButton("Confirm");
		btnConfirm.setEnabled(false);
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateReport();
			}
		});
		add(btnConfirm, "flowx,cell 0 6");
		btnConfirm.setEnabled(true);
	}

	@Override
	public void onShow() {

	}

	private void validateFields() throws ParseException, FieldEmptyException, DateTimeException {
		if (cbStation.getSelectedIndex() == -1 || cbCurrency.getSelectedIndex() == -1
				|| cbVehicleCategory.getSelectedIndex() == -1) {
			throw new FieldEmptyException();
		}

		Date tempStartDate;
		Date tempEndDate;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			tempStartDate = formatter.parse(txtStartDate.getText().trim());
			tempEndDate = formatter.parse(txtEndDate.getText().trim());
		} catch (ParseException e) {
			throw e;
		}

		if (tempStartDate.after(tempEndDate)) {
			throw new DateTimeException("Invalid date interval");
		}
		
		this.startDate = tempStartDate;
		this.endDate = tempEndDate;
	}

	private void generateReport() {
		try {
			validateFields();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Incorrect date format. Must be dd.MM.yyyy", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (FieldEmptyException e) {
			JOptionPane.showMessageDialog(null, "Field cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (DateTimeException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		getDataForReport();
	}
	
	private void getDataForReport() {
		List<Station> stations;
		List<PricelistEntry.VehicleCategory> categories;
		
		if (cbStation.getSelectedItem().equals("ALL")) {
			stations = ctx.getStationService().getByType(Type.EXIT);
		}
		else {
			stations = new ArrayList<Station>();
			stations.add((Station) ctx.getStationService().getByString(cbStation.getSelectedItem().toString()));
		}
		if (cbVehicleCategory.getSelectedItem().toString().equals("ALL")) {
			categories = Arrays.asList(PricelistEntry.VehicleCategory.values());
		}
		else {
			categories = new ArrayList<PricelistEntry.VehicleCategory>();
			categories.add((PricelistEntry.VehicleCategory) cbVehicleCategory.getSelectedItem());
		}
		PricelistEntry.Currency currency = (PricelistEntry.Currency) cbCurrency.getSelectedItem(); 
		
		ReportXYChart report;
		try {
			report = new ReportXYChart(stations, categories, currency, this.startDate, this.endDate, ctx);
			
			Thread t = new Thread(new Runnable() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public void run() {
					new SwingWrapper(report.chart).displayChart()
							.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				}
			});
			t.start();
			
			txtProfit.setText(String.valueOf(report.totalProfit));
		} catch (EmptyAxisException e) {
			JOptionPane.showMessageDialog(null, "There is no data to show", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
}
