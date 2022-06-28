package desktop.collector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import core.AppContext;
import core.booth.Booth;
import core.employee.Employee;
import core.pricelist.entry.PricelistEntry;
import core.ticket.Ticket;
import core.util.IObserver;
import desktop.ITabbedPanel;
import net.miginfocom.swing.MigLayout;

public class ExitBoothView extends JPanel implements ITabbedPanel, IObserver {

	private static final long serialVersionUID = 2944929748859554409L;

	private AppContext ctx;
	private Employee collector;
	private Booth booth;
	private JTextField txtEntryBooth;
	private JTextField txtArrivedAt;
	private JTextField txtCost;
	private JTextField txtPaid;
	private JTextField txtChange;
	private JTextField txtAverageSpeed;
	private JComboBox<PricelistEntry.VehicleCategory> cbVehicleCategory;
	private JComboBox<PricelistEntry.Currency> cbCurrency;

	public ExitBoothView(AppContext ctx, Employee collector, Booth booth) {
		if (collector.getRole() != Employee.Role.COLLECTOR) {
			throw new IllegalArgumentException("Employee must be of type COLLECTOR");
		}

		this.ctx = ctx;
		this.collector = collector;
		this.booth = booth;

		initGUI();
	}

	private void initGUI() {
		setLayout(new MigLayout("", "[grow]", "[][][][]"));

		JLabel lblBooth = new JLabel(String.format("Booth: %s", booth.getCode()));
		add(lblBooth, "cell 0 0");

		JLabel lblEntryBooth = new JLabel("Vehicle's entry booth:");
		add(lblEntryBooth, "flowx,cell 0 1");

		txtEntryBooth = new JTextField();
		add(txtEntryBooth, "cell 0 1,growx");
		txtEntryBooth.setColumns(10);

		JLabel lblArrivedAt = new JLabel("Arrived here at:");
		add(lblArrivedAt, "flowx,cell 0 2");

		txtArrivedAt = new JTextField();
		add(txtArrivedAt, "cell 0 2,growx");
		txtArrivedAt.setColumns(10);

		JLabel lblAverageSpeed = new JLabel("Average speed (km/h):");
		add(lblAverageSpeed, "flowx,cell 0 3");

		txtAverageSpeed = new JTextField();
		add(txtAverageSpeed, "cell 0 3,growx");
		txtAverageSpeed.setColumns(10);

		JLabel lblSelectCategory = new JLabel("Select vehicle category:");
		add(lblSelectCategory, "flowx,cell 0 4");

		cbVehicleCategory = new JComboBox<PricelistEntry.VehicleCategory>(PricelistEntry.VehicleCategory.values());
		cbVehicleCategory.setSelectedItem(null);
		add(cbVehicleCategory, "cell 0 4,growx");

		JLabel lblCurrency = new JLabel("Select currency:");
		add(lblCurrency, "flowx,cell 0 5");

		cbCurrency = new JComboBox<PricelistEntry.Currency>(PricelistEntry.Currency.values());
		cbCurrency.setSelectedItem(null);
		add(cbCurrency, "cell 0 5,growx");

		JLabel lblCost = new JLabel("Cost:");
		add(lblCost, "flowx,cell 0 6");

		txtCost = new JTextField();
		add(txtCost, "cell 0 6,growx");
		txtCost.setColumns(10);

		JLabel lblPaid = new JLabel("Paid:");
		add(lblPaid, "flowx,cell 0 7");

		txtPaid = new JTextField();
		add(txtPaid, "cell 0 7,growx");
		txtPaid.setColumns(10);

		JLabel lblChange = new JLabel("Change:");
		add(lblChange, "flowx,cell 0 8");

		txtChange = new JTextField();
		add(txtChange, "cell 0 8,growx");
		txtChange.setColumns(10);

		JButton btnConfirm = new JButton("Confirm");
		add(btnConfirm, "flowx,cell 0 9");

		JButton btnReject = new JButton("Reject");
		add(btnReject, "cell 0 9");
	}

	@Override
	public void onShow() {

	}

	@Override
	public void update(Object e) {
		
	}

}
