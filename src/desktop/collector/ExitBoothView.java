package desktop.collector;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.AppContext;
import core.booth.Booth;
import core.booth.DeviceStatus.Type;
import core.booth.observer.IBoothObserver;
import core.employee.Employee;
import core.malfunction.Malfunction;
import core.payment.Payment;
import core.pricelist.entry.PricelistEntry;
import core.station.Station;
import core.ticket.Ticket;
import core.util.IObserver;
import desktop.ITabbedPanel;
import net.miginfocom.swing.MigLayout;

public class ExitBoothView extends JPanel implements ITabbedPanel, IObserver, IBoothObserver {

	private static final long serialVersionUID = 2944929748859554409L;

	private AppContext ctx;
	private Employee collector;
	private Booth booth;
	private JTextField txtEntryBooth;
	private JTextField txtArrivedAt;
	private JTextField txtCost;
	private JSpinner spnPaid;
	private JTextField txtChange;
	private JTextField txtAverageSpeed;
	private JComboBox<PricelistEntry.VehicleCategory> cbVehicleCategory;
	private JComboBox<PricelistEntry.Currency> cbCurrency;
	private JButton btnConfirm;
	private JButton btnNextTicket;
	private JButton btnFlipSemaphore;
	private JButton btnFlipRamp;
	private JButton btnFlipScreen;
	private JLabel lblSemaphore;
	private JLabel lblRamp;
	private JLabel lblScreen;

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
		this.booth.initDeviceStatus();
		booth.addObserver(this);
		booth.activate();
		this.processedTicket = null;
		this.entryForTicket = null;
		this.queuedTickets = new ArrayList<Ticket>();

		initGUI();
	}

	private void initGUI() {
		setLayout(new MigLayout("debug", "[grow]", "[][][][]"));

		JLabel lblBooth = new JLabel(String.format("Booth: %s", booth.getCode()));
		add(lblBooth, "cell 0 0, span 3");

		JLabel lblEntryBooth = new JLabel("Vehicle's entry booth:");
		add(lblEntryBooth, "flowx,cell 0 1, span 3");

		txtEntryBooth = new JTextField();
		txtEntryBooth.setEnabled(false);
		add(txtEntryBooth, "cell 0 1,growx, span 3");
		txtEntryBooth.setColumns(10);

		JLabel lblArrivedAt = new JLabel("Arrived at:");
		add(lblArrivedAt, "flowx,cell 0 2, span 3");

		txtArrivedAt = new JTextField();
		txtArrivedAt.setEnabled(false);
		add(txtArrivedAt, "cell 0 2,growx, span 3");
		txtArrivedAt.setColumns(10);

		JLabel lblAverageSpeed = new JLabel("Average speed (km/h):");
		add(lblAverageSpeed, "flowx,cell 0 3, span 3");

		txtAverageSpeed = new JTextField();
		txtAverageSpeed.setEnabled(false);
		add(txtAverageSpeed, "cell 0 3,growx, span 3");
		txtAverageSpeed.setColumns(10);

		JLabel lblSelectCategory = new JLabel("Select vehicle category:");
		add(lblSelectCategory, "flowx,cell 0 4, span 3");

		cbVehicleCategory = new JComboBox<PricelistEntry.VehicleCategory>(PricelistEntry.VehicleCategory.values());
		cbVehicleCategory.setSelectedItem(null);
		cbVehicleCategory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fillPaymentFields();
			}
		});
		add(cbVehicleCategory, "cell 0 4,growx, span 3");

		JLabel lblCurrency = new JLabel("Select currency:");
		add(lblCurrency, "flowx,cell 0 5, span 3");

		cbCurrency = new JComboBox<PricelistEntry.Currency>(PricelistEntry.Currency.values());
		cbCurrency.setSelectedItem(null);
		cbCurrency.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fillPaymentFields();
			}
		});
		add(cbCurrency, "cell 0 5,growx, span 3");

		JLabel lblCost = new JLabel("Cost:");
		add(lblCost, "flowx,cell 0 6, span 3");

		txtCost = new JTextField();
		txtCost.setEnabled(false);
		add(txtCost, "cell 0 6,growx, span 3");
		txtCost.setColumns(10);

		JLabel lblPaid = new JLabel("Paid:");
		add(lblPaid, "flowx,cell 0 7, span 3");

		spnPaid = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		spnPaid.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateChange();
			}
		});
		add(spnPaid, "cell 0 7,growx, span 3");

		JLabel lblChange = new JLabel("Change:");
		add(lblChange, "flowx,cell 0 8, span 3");

		txtChange = new JTextField();
		txtChange.setEnabled(false);
		add(txtChange, "cell 0 8,growx, span 3");
		txtChange.setColumns(10);

		btnConfirm = new JButton("Confirm");
		btnConfirm.setEnabled(false);
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processPayment();
				simulateVehiclePassing();
			}
		});
		add(btnConfirm, "flowx,cell 0 9, span 3");

		btnNextTicket = new JButton("Next ticket");
		btnNextTicket.setEnabled(true);
		btnNextTicket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadNextTicket();
				fillPaymentFields();
			}
		});
		add(btnNextTicket, "flowx, wrap, cell 0 9, span 3");
		
		
		lblSemaphore = new JLabel();
		lblRamp = new JLabel();
		lblScreen = new JLabel();
		updateDeviceLabels();

		btnFlipSemaphore = new JButton("Toggle");
		btnFlipSemaphore.setEnabled(true);
		btnFlipSemaphore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				booth.flipDeviceFlags(Type.SEMAPHORE);
			}
		});

		btnFlipRamp = new JButton("Toggle");
		btnFlipRamp.setEnabled(true);
		btnFlipRamp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				booth.flipDeviceFlags(Type.RAMP);
			}
		});

		btnFlipScreen = new JButton("Toggle");
		btnFlipScreen.setEnabled(true);
		btnFlipScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				booth.flipDeviceFlags(Type.SCREEN);
			}
		});
		
		add(lblSemaphore, "cell 0 10");
		add(lblRamp, "cell 1 10");
		add(lblScreen, "cell 2 10");
		add(btnFlipSemaphore, "cell 0 11");
		add(btnFlipRamp, "cell 1 11");
		add(btnFlipScreen, "cell 2 11");
	}
	
	private void updateDeviceLabels() {
		lblSemaphore.setText("Semaphore: " + (booth.getDeviceFlags(Type.SEMAPHORE) == 1 ? "Green" : "Red"));
		lblRamp.setText("Ramp: " + (booth.getDeviceFlags(Type.RAMP) == 1 ? "Raised" : "Lowered"));
		lblScreen.setText("Screen: " + (booth.getDeviceFlags(Type.SCREEN) == 1 ? "Active" : "Inactive"));
	}

	@Override
	public void onShow() {

	}

	private void updateChange() {
		if (this.entryForTicket == null)
			return;
		int change = (int) spnPaid.getValue() - this.entryForTicket.getPrice();
		txtChange.setText(String.valueOf(change));
		checkIfCanProcess();
	}

	private void checkIfCanProcess() {
		if (Integer.decode(txtChange.getText()) >= 0) {
			btnConfirm.setEnabled(true);
		} else {
			btnConfirm.setEnabled(false);
		}
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

		LocalDateTime enteredAt = this.processedTicket.getEnteredAt();
		String datetime = enteredAt.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

		txtArrivedAt.setText(datetime);
		txtAverageSpeed.setText(String.valueOf(Math.abs(new Random().nextInt()) % 60 + 1));
	}

	private void resetFields() {
		txtEntryBooth.setText("");
		txtArrivedAt.setText("");
		txtAverageSpeed.setText("");
		txtCost.setText("");
		spnPaid.setValue(0);
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
		if (this.entryForTicket == null) {
			JOptionPane.showMessageDialog(null, "Couldn't find pricelist entry.");
			return;
		}

		txtCost.setText(String.valueOf(this.entryForTicket.getPrice()));

		int paid = (int) ((Math.random() + 1) * this.entryForTicket.getPrice());
		spnPaid.setValue(paid);

		int change = paid - this.entryForTicket.getPrice();
		txtChange.setText(String.valueOf(change));

		checkIfCanProcess();
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

		int amount = (int) spnPaid.getValue();

		ctx.getPaymentService().add(new Payment(now, entryForTicket, processedTicket, collector, amount));

		processedTicket = null;
		entryForTicket = null;
		resetFields();
	}

	private void simulateVehiclePassing() {
		JOptionPane waitOpt = new JOptionPane("Please wait while vehicle is passing", JOptionPane.PLAIN_MESSAGE,
				JOptionPane.DEFAULT_OPTION);
		JDialog waitDialog = waitOpt.createDialog("Please wait");
		waitDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		waitDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		booth.vehicleStartedPassing();
		Timer t = new Timer(1500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				booth.vehiclePassed();
				waitDialog.setVisible(false);
				waitDialog.dispose();
				JOptionPane.showMessageDialog(null, "Vehicle passed successfully.");
			}
		});
		t.setRepeats(false);
		t.start();
		waitDialog.setVisible(true);
	}

	@Override
	public void notify(Malfunction malf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyState() {
		// TODO Auto-generated method stub
	}

	@Override
	public void notifyDevice() {
		updateDeviceLabels();
	}

}
