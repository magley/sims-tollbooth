package desktop.report;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import core.AppContext;
import core.pricelist.entry.PricelistEntry;
import core.station.Station;
import desktop.ITabbedPanel;
import net.miginfocom.swing.MigLayout;

public class ReportDashboardView extends JPanel implements ITabbedPanel {

	private static final long serialVersionUID = 4893604555701933671L;

	private AppContext ctx;
	private Station station;

	private JComboBox<Station> cbStation;
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
		//TODO REORDER
		JLabel lblStation = new JLabel(String.format("Station: %s", station.getCode()));
		add(lblStation, "cell 0 0");
		
		//TODO add option: all stations
		cbStation = new JComboBox<Station>(ctx.getStationService().getByType(Station.Type.ENTER).toArray(new Station[0]));
		add(cbStation, "cell 0 1,growx");	

		JLabel lblStartDate = new JLabel("From date:");
		add(lblStartDate, "flowx,cell 0 2");
		
		JLabel lblEndDate = new JLabel("To date:");
		add(lblEndDate, "flowx,cell 0 2");

		JLabel lblSelectCategory = new JLabel("Select vehicle category:");
		add(lblSelectCategory, "flowx,cell 0 4");

		//TODO add option: all categories
		cbVehicleCategory = new JComboBox<PricelistEntry.VehicleCategory>(PricelistEntry.VehicleCategory.values());
		cbVehicleCategory.setSelectedItem(null);
		cbVehicleCategory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fillFields();
			}
		});
		add(cbVehicleCategory, "cell 0 4,growx");

		JLabel lblCurrency = new JLabel("Select currency:");
		add(lblCurrency, "flowx,cell 0 5");

		cbCurrency = new JComboBox<PricelistEntry.Currency>(PricelistEntry.Currency.values());
		cbCurrency.setSelectedItem(null);
		cbCurrency.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fillFields();
			}
		});
		add(cbCurrency, "cell 0 5,growx");

		JLabel lblProfit = new JLabel("Total profit:");
		add(lblProfit, "flowx,cell 0 6");

		txtProfit = new JTextField();
		txtProfit.setEnabled(false);
		add(txtProfit, "cell 0 6,growx");
		txtProfit.setColumns(10);

		JLabel lblChange = new JLabel("Change:");
		add(lblChange, "flowx,cell 0 8");
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.setEnabled(false);
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateReport();
			}
		});
		add(btnConfirm, "flowx,cell 0 9");
	}

	@Override
	public void onShow() {

	}
	
	private void checkIfCanProcess() {
		if (Integer.decode(txtProfit.getText()) >= 0) {
			btnConfirm.setEnabled(true);
		} else {
			btnConfirm.setEnabled(false);
		}
	}

	private void resetFields() {
		txtStartDate.setText("");
		txtEndDate.setText("");
		txtProfit.setText("");
		cbVehicleCategory.setSelectedItem(null);
		cbCurrency.setSelectedItem(null);
		btnConfirm.setEnabled(false);
	}

	private void fillFields() {
		if (cbStation.getSelectedIndex() == -1 || cbCurrency.getSelectedIndex() == -1
				|| cbVehicleCategory.getSelectedIndex() == -1) {
			return;
		}

		getEntry();

		txtProfit.setText(String.valueOf("100")); 	//TODO setProfit with corresponding value

		checkIfCanProcess();
	}

	private void getEntry() {
		Station station = null; 		//TODO get selected station
		PricelistEntry.VehicleCategory category = (PricelistEntry.VehicleCategory) cbVehicleCategory.getSelectedItem();
		PricelistEntry.Currency currency = (PricelistEntry.Currency) cbCurrency.getSelectedItem();
		//TODO get rest of the data
		
		//this.inputStation = station;
		//this.inputCategory = category;
		//this.inputCurrency = currency;
		//TODO set rest of the data
	}

	private void generateReport() {
		
		//TODO: create a chart instance and a thread to run it
		
		resetFields();
	}

}
