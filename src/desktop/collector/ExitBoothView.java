package desktop.collector;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
import core.booth.DeviceStatus.Status;
import core.booth.DeviceStatus.Type;
import core.booth.observer.IBoothObserver;
import core.employee.Employee;
import core.malfunction.Malfunction;
import core.payment.Payment;
import core.pricelist.entry.PricelistEntry;
import core.station.Station;
import core.ticket.Ticket;
import core.tollsegment.TollSegment;
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
	private JButton btnMalfunctionSemaphore;
	private JButton btnMalfunctionRamp;
	private JButton btnMalfunctionScreen;
	private JButton btnFixSemaphore;
	private JButton btnFixRamp;
	private JButton btnFixScreen;
	private JLabel lblSemaphore;
	private JLabel lblRamp;
	private JLabel lblScreen;
	private JLabel lblSemaphoreWorking;
	private JLabel lblRampWorking;
	private JLabel lblScreenWorking;

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
		lblSemaphore = new JLabel();
		lblRamp = new JLabel();
		lblScreen = new JLabel();
		lblSemaphoreWorking = new JLabel();
		lblRampWorking = new JLabel();
		lblScreenWorking = new JLabel();
		txtChange = new JTextField();
		btnConfirm = new JButton("Confirm");
		btnConfirm.setEnabled(false);
		btnNextTicket = new JButton("Next ticket");
		btnNextTicket.setEnabled(true);

		btnFlipSemaphore = new JButton("Toggle");
		btnFlipSemaphore.setEnabled(true);
		btnFlipRamp = new JButton("Toggle");
		btnFlipRamp.setEnabled(true);
		btnFlipScreen = new JButton("Toggle");
		btnFlipScreen.setEnabled(true);

		btnMalfunctionSemaphore = new JButton("Report Error");
		btnMalfunctionSemaphore.setEnabled(true);
		btnMalfunctionRamp = new JButton("Report Error");
		btnMalfunctionRamp.setEnabled(true);
		btnMalfunctionScreen = new JButton("Report Error");
		btnMalfunctionScreen.setEnabled(true);

		btnFixSemaphore = new JButton("Fix");
		btnFixSemaphore.setEnabled(false);
		btnFixRamp = new JButton("Fix");
		btnFixRamp.setEnabled(false);
		btnFixScreen = new JButton("Fix");
		btnFixScreen.setEnabled(false);

		booth.activate();
		this.processedTicket = null;
		this.entryForTicket = null;
		this.queuedTickets = new ArrayList<Ticket>();

		initGUI();
	}

	private void initGUI() {
		setLayout(new MigLayout("",
				"[sizegroup main, grow][sizegroup main, grow][sizegroup main, grow]", "[][][][]"));

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

		txtChange.setEnabled(false);
		add(txtChange, "cell 0 8,growx, span 3");
		txtChange.setColumns(10);

		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processPayment();
				simulateVehiclePassing();
			}
		});
		add(btnConfirm, "flowx,cell 0 9, span 3");

		btnNextTicket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadNextTicket();
				fillPaymentFields();
			}
		});
		add(btnNextTicket, "flowx, wrap, cell 0 9, span 3");
		
		updateDeviceLabelsAndButtons();
		
		btnFlipSemaphore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				booth.flipDeviceFlags(Type.SEMAPHORE);
			}
		});

		btnFlipRamp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				booth.flipDeviceFlags(Type.RAMP);
			}
		});

		btnFlipScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (booth.isNotDeactivated())
					booth.pause();
				else
					booth.activate();
			}
		});
		

		btnMalfunctionSemaphore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Malfunction malf = new Malfunction(booth.getDeviceStatus(Type.SEMAPHORE), booth, collector);
				ctx.getMalfunctionService().add(malf);
			}
		});
		btnMalfunctionRamp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Malfunction malf = new Malfunction(booth.getDeviceStatus(Type.RAMP), booth, collector);
				ctx.getMalfunctionService().add(malf);
			}
		});
		btnMalfunctionScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Malfunction malf = new Malfunction(booth.getDeviceStatus(Type.SCREEN), booth, collector);
				ctx.getMalfunctionService().add(malf);
			}
		});

		btnFixSemaphore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				booth.setDeviceStatus(Type.SEMAPHORE, Status.WORKING);
			}
		});
		btnFixRamp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				booth.setDeviceStatus(Type.RAMP, Status.WORKING);
			}
		});
		btnFixScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				booth.setDeviceStatus(Type.SCREEN, Status.WORKING);
			}
		});

		add(lblSemaphore, "cell 0 10");
		add(lblRamp, "cell 1 10");
		add(lblScreen, "cell 2 10");
		add(lblSemaphoreWorking, "cell 0 11");
		add(lblRampWorking, "cell 1 11");
		add(lblScreenWorking, "cell 2 11");
		add(btnFlipSemaphore, "cell 0 12");
		add(btnFlipRamp, "cell 1 12");
		add(btnFlipScreen, "cell 2 12");
		add(btnMalfunctionSemaphore, "cell 0 13");
		add(btnMalfunctionRamp, "cell 1 13");
		add(btnMalfunctionScreen, "cell 2 13");
		add(btnFixSemaphore, "cell 0 14");
		add(btnFixRamp, "cell 1 14");
		add(btnFixScreen, "cell 2 14");
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				super.componentShown(e);
				btnNextTicket.requestFocus();
			}
		});
	}
	
	public void onAdd() {
		getRootPane().setDefaultButton(btnConfirm);
	}
	
	private void updateDeviceLabelsAndButtons() {
		lblSemaphore.setText("Semaphore: " + (booth.getDeviceFlags(Type.SEMAPHORE) == 1 ? "Green" : "Red"));
		lblRamp.setText("Ramp: " + (booth.getDeviceFlags(Type.RAMP) == 1 ? "Raised" : "Lowered"));

		if (booth.getDeviceFlags(Type.SCREEN) == 1) {
			lblScreen.setText("Screen: Active");
			lblScreen.setForeground(Color.BLACK);
		} else {
			lblScreen.setText("Screen: Inactive");
			lblScreen.setForeground(Color.YELLOW);
		}

		if (!booth.getDeviceStatus(Type.SEMAPHORE).getStatus().equals(Status.WORKING)) {
			lblSemaphoreWorking.setText("NOT WORKING");
			lblSemaphoreWorking.setForeground(Color.RED);
		} else {
			lblSemaphoreWorking.setText("Working");
			lblSemaphoreWorking.setForeground(Color.BLACK);
		}
		if (!booth.getDeviceStatus(Type.RAMP).getStatus().equals(Status.WORKING)) {
			lblRampWorking.setText("NOT WORKING");
			lblRampWorking.setForeground(Color.RED);
		} else {
			lblRampWorking.setText("Working");
			lblRampWorking.setForeground(Color.BLACK);
		}
		if (!booth.getDeviceStatus(Type.SCREEN).getStatus().equals(Status.WORKING)) {
			lblScreenWorking.setText("NOT WORKING");
			lblScreenWorking.setForeground(Color.RED);
		} else {
			lblScreenWorking.setText("Working");
			lblScreenWorking.setForeground(Color.BLACK);
		}

		Boolean semaphoreWorking = booth.getDeviceStatus(Type.SEMAPHORE).getStatus().equals(Status.WORKING);
		btnFlipSemaphore.setEnabled(semaphoreWorking);
		btnMalfunctionSemaphore.setEnabled(semaphoreWorking);
		btnFixSemaphore.setEnabled(!semaphoreWorking);
		Boolean rampWorking = booth.getDeviceStatus(Type.RAMP).getStatus().equals(Status.WORKING);
		btnFlipRamp.setEnabled(rampWorking);
		btnMalfunctionRamp.setEnabled(rampWorking);
		btnFixRamp.setEnabled(!rampWorking);
		Boolean screenWorking = booth.getDeviceStatus(Type.SCREEN).getStatus().equals(Status.WORKING);
		btnFlipScreen.setEnabled(screenWorking && !booth.anyDeviceNotWorking());
		btnMalfunctionScreen.setEnabled(screenWorking);
		btnFixScreen.setEnabled(!screenWorking);
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
		int change;
		try {
			change = Integer.decode(txtChange.getText());
		} catch (NumberFormatException e) {
			change = -1;
		}
		if (booth.isNotDeactivated() && !booth.isVehiclePassing() && change >= 0) {
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
		cbVehicleCategory.requestFocus();
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
		// assumes ticket is loaded
		txtEntryBooth.setText(this.processedTicket.getEntryBooth().getCode());

		LocalDateTime enteredAt = this.processedTicket.getEnteredAt();
		String datetime = enteredAt.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		txtArrivedAt.setText(datetime);

		Station entryStation = processedTicket.getEntryBooth().getStation();
		Station exitStation = booth.getStation();
		TollSegment segment = ctx.getTollSegmentService().getFor(entryStation, exitStation);

		if (segment == null) {
			JOptionPane.showMessageDialog(null, "Fraud detected! Invalid segment!", "Fraud detected", JOptionPane.WARNING_MESSAGE);
			resetFields();
			btnNextTicket.requestFocus();
		} else {
			Duration timeElapsed = Duration.between(enteredAt, LocalDateTime.now());
			// multiply by 3600 to get from km/s -> km/h
			txtAverageSpeed.setText(String.format(
					"%.2f", 3600 * segment.getDistance() / ((double) timeElapsed.toSeconds()))
					+ " km/h");
		}
	}

	private void resetFields() {
		processedTicket = null;
		entryForTicket = null;
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
		TollSegment segment = ctx.getTollSegmentService().getFor(entryStation, exitStation);

		if (segment == null) {
			JOptionPane.showMessageDialog(null, "Fraud detected! Invalid segment!", "Fraud detected", JOptionPane.WARNING_MESSAGE);
			resetFields();
			btnNextTicket.requestFocus();
		} else {
			this.entryForTicket = ctx.getPricelistService().getFor(segment, category, currency);
		}
	}

	private void processPayment() {
		LocalDateTime now = LocalDateTime.now();
		this.processedTicket.setLeftAt(now);

		int amount = (int) spnPaid.getValue();

		ctx.getPaymentService().add(new Payment(now, entryForTicket, processedTicket, collector, amount));
	}

	private void simulateVehiclePassing() {
		booth.vehicleStartedPassing();
		Timer t = new Timer(1500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				booth.vehiclePassed();
				JOptionPane.showMessageDialog(null, "Vehicle passed successfully.");
				resetFields();
				btnNextTicket.requestFocus();
			}
		});
		t.setRepeats(false);
		t.start();
	}

	@Override
	public void notify(Malfunction malf) {
		updateDeviceLabelsAndButtons();
	}

	@Override
	public void notifyState() {
		checkIfCanProcess();
		if (!booth.isNotDeactivated()) {
			btnNextTicket.setEnabled(false);
		} else if (processedTicket == null) {
			btnNextTicket.setEnabled(true);
		}
	}

	@Override
	public void notifyDevice() {
		updateDeviceLabelsAndButtons();
	}

}
