package desktop.collector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import core.AppContext;
import core.booth.Booth;
import core.employee.Employee;
import core.payment.Payment;
import core.pricelist.entry.PricelistEntry;
import core.station.Station;
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
	private JButton btnConfirm;
	private JButton btnNextTicket;

	private Ticket processedTicket;
	private PricelistEntry entryForTicket;
	private List<Ticket> queuedTickets;

	public ExitBoothView(AppContext ctx, Employee collector, Booth booth) {
		if (collector.getRole() != Employee.Role.COLLECTOR) {
			throw new IllegalArgumentException("Employee must be of type COLLECTOR");
		}

		this.ctx = ctx;
		this.collector = collector;
		this.booth = booth;
		this.processedTicket = null;
		this.entryForTicket = null;
		this.queuedTickets = new ArrayList<Ticket>();

		initGUI();
	}

	private void initGUI() {
		setLayout(new MigLayout("", "[grow]", "[][][][]"));

		JLabel lblBooth = new JLabel(String.format("Booth: %s", booth.getCode()));
		add(lblBooth, "cell 0 0");

		JLabel lblEntryBooth = new JLabel("Vehicle's entry booth:");
		add(lblEntryBooth, "flowx,cell 0 1");

		txtEntryBooth = new JTextField();
		txtEntryBooth.setEnabled(false);
		add(txtEntryBooth, "cell 0 1,growx");
		txtEntryBooth.setColumns(10);

		JLabel lblArrivedAt = new JLabel("Arrived here at:");
		add(lblArrivedAt, "flowx,cell 0 2");

		txtArrivedAt = new JTextField();
		txtArrivedAt.setEnabled(false);
		add(txtArrivedAt, "cell 0 2,growx");
		txtArrivedAt.setColumns(10);

		JLabel lblAverageSpeed = new JLabel("Average speed (km/h):");
		add(lblAverageSpeed, "flowx,cell 0 3");

		txtAverageSpeed = new JTextField();
		txtAverageSpeed.setEnabled(false);
		add(txtAverageSpeed, "cell 0 3,growx");
		txtAverageSpeed.setColumns(10);

		JLabel lblSelectCategory = new JLabel("Select vehicle category:");
		add(lblSelectCategory, "flowx,cell 0 4");

		cbVehicleCategory = new JComboBox<PricelistEntry.VehicleCategory>(PricelistEntry.VehicleCategory.values());
		cbVehicleCategory.setSelectedItem(null);
		cbVehicleCategory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fillPaymentFields();
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
				fillPaymentFields();
			}
		});
		add(cbCurrency, "cell 0 5,growx");

		JLabel lblCost = new JLabel("Cost:");
		add(lblCost, "flowx,cell 0 6");

		txtCost = new JTextField();
		txtCost.setEnabled(false);
		add(txtCost, "cell 0 6,growx");
		txtCost.setColumns(10);

		JLabel lblPaid = new JLabel("Paid:");
		add(lblPaid, "flowx,cell 0 7");

		txtPaid = new JTextField();
		txtPaid.setEnabled(false);
		add(txtPaid, "cell 0 7,growx");
		txtPaid.setColumns(10);

		JLabel lblChange = new JLabel("Change:");
		add(lblChange, "flowx,cell 0 8");

		txtChange = new JTextField();
		txtChange.setEnabled(false);
		add(txtChange, "cell 0 8,growx");
		txtChange.setColumns(10);

		btnConfirm = new JButton("Confirm");
		btnConfirm.setEnabled(false);
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processPayment();
			}
		});
		add(btnConfirm, "flowx,cell 0 9");

		btnNextTicket = new JButton("Next ticket");
		btnNextTicket.setEnabled(true);
		btnNextTicket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadNextTicket();
			}
		});
		add(btnNextTicket, "flowx,cell 0 9");
	}

	@Override
	public void onShow() {

	}

	public void loadNextTicket() {
		if (this.queuedTickets.size() == 0) {
			JOptionPane.showMessageDialog(null, "No tickets in queue.");
			return;
		}
		this.processedTicket = this.queuedTickets.remove(0);
		btnNextTicket.setEnabled(false);
		fillFields();
	}

	@Override
	public void update(Object e) {
		Ticket t = (Ticket) e;
		if (t.getExitBooth() != this.booth) {
			return;
		}
		this.queuedTickets.add(t);
	}

	private void fillFields() {
		txtEntryBooth.setText(this.processedTicket.getEntryBooth().getCode());

		LocalDateTime now = LocalDateTime.now();
		String datetime = now.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

		txtArrivedAt.setText(datetime);
		txtAverageSpeed.setText(String.valueOf(Math.abs(new Random().nextInt()) % 60 + 1));
	}

	private void resetFields() {
		txtEntryBooth.setText("");
		txtArrivedAt.setText("");
		txtAverageSpeed.setText("");
		txtCost.setText("");
		txtPaid.setText("");
		txtChange.setText("");
		cbVehicleCategory.setSelectedItem(null);
		cbCurrency.setSelectedItem(null);
		btnConfirm.setEnabled(false);
		btnNextTicket.setEnabled(true);
	}

	private void fillPaymentFields() {
		if (this.processedTicket == null || cbCurrency.getSelectedIndex() == -1
				|| cbVehicleCategory.getSelectedIndex() == -1) {
			return;
		}

		getPricelistEntry();

		txtCost.setText(String.valueOf(this.entryForTicket.getPrice()));

		int paid = (int) ((Math.random() + 1) * this.entryForTicket.getPrice());
		txtPaid.setText(String.valueOf(paid));

		int change = paid - this.entryForTicket.getPrice();
		txtChange.setText(String.valueOf(change));

		this.btnConfirm.setEnabled(true);
	}

	private void getPricelistEntry() {
		Station entryStation = processedTicket.getEntryBooth().getStation();
		Station exitStation = booth.getStation();
		PricelistEntry.VehicleCategory category = (PricelistEntry.VehicleCategory) cbVehicleCategory.getSelectedItem();
		PricelistEntry.Currency currency = (PricelistEntry.Currency) cbCurrency.getSelectedItem();

		this.entryForTicket = ctx.getPricelistService().getFor(entryStation, exitStation, category, currency);
	}

	private void processPayment() {
		LocalDateTime now = LocalDateTime.now();
		this.processedTicket.setLeftAt(now);

		int amount = Integer.decode(txtPaid.getText());

		ctx.getPaymentService().add(new Payment(now, entryForTicket, processedTicket, collector, amount));

		this.processedTicket = null;
		this.entryForTicket = null;

		JOptionPane.showMessageDialog(null, "Successfully processed payment.");
		resetFields();
	}

}
