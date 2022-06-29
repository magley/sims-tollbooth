package desktop.pricelist.entry;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.AppContext;
import core.pricelist.Pricelist;
import core.pricelist.entry.PricelistEntry;
import core.station.Station;
import desktop.ITabbedPanel;
import net.miginfocom.swing.MigLayout;

public class PricelistEntryDashboardView extends JPanel implements ITabbedPanel {

	private static final long serialVersionUID = -7208084613103550459L;

	private AppContext ctx;

	private PricelistEntryTableModel tableModel;
	private JTable table;

	private JList<Pricelist> lstPricelist;
	private JComboBox<Station> cbEntry;
	private JComboBox<Station> cbExit;
	private JComboBox<PricelistEntry.VehicleCategory> cbCategory;
	private JComboBox<PricelistEntry.Currency> cbCurrency;
	private JSpinner spnPrice;

	public PricelistEntryDashboardView(AppContext ctx) {
		this.ctx = ctx;
		setLayout(new MigLayout("", "[grow]", "[grow][][][]"));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "grow,flowy,cell 0 0");

		tableModel = new PricelistEntryTableModel(ctx.getPricelistEntryService());
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (event.getValueIsAdjusting()) {
					return;
				}
				if (table.getSelectedRow() >= 0 && table.getSelectedRowCount() == 1)
					tableSelectedRow(table.getSelectedRow());
				else
					tableDeselectRow();
			}
		});
		scrollPane.setViewportView(table);

		JScrollPane scrollPaneList = new JScrollPane();
		add(scrollPaneList, "cell 1 0,grow");

		lstPricelist = new JList<Pricelist>(ctx.getPricelistService().getAll().toArray(new Pricelist[0]));
		lstPricelist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstPricelist.setCellRenderer(new PricelistListRenderer(ctx.getPricelistService()));
		scrollPaneList.setViewportView(lstPricelist);

		JLabel lblEntry = new JLabel("Entry: ");
		add(lblEntry, "flowx,cell 0 1");

		cbEntry = new JComboBox<Station>(ctx.getStationService().getByType(Station.Type.ENTER).toArray(new Station[0]));
		add(cbEntry, "cell 0 1,growx");

		JLabel lblExit = new JLabel("Exit: ");
		add(lblExit, "flowx,cell 0 2");

		cbExit = new JComboBox<Station>(ctx.getStationService().getByType(Station.Type.EXIT).toArray(new Station[0]));
		add(cbExit, "cell 0 2,growx");

		JLabel lblCategory = new JLabel("Category:");
		add(lblCategory, "flowx,cell 0 3");

		cbCategory = new JComboBox<PricelistEntry.VehicleCategory>(PricelistEntry.VehicleCategory.values());
		add(cbCategory, "cell 0 3,growx");

		JLabel lblCurrency = new JLabel("Currency:");
		add(lblCurrency, "flowx,cell 0 4");

		cbCurrency = new JComboBox<PricelistEntry.Currency>(PricelistEntry.Currency.values());
		add(cbCurrency, "cell 0 4,growx");

		JLabel lblPrice = new JLabel("Price:");
		add(lblPrice, "flowx,cell 0 5");

		spnPrice = new JSpinner();
		spnPrice.setModel(new SpinnerNumberModel(0, 0, null, 1));
		add(spnPrice, "cell 0 5,growx");

		JButton btnInsert = new JButton("Insert");
		btnInsert.setEnabled(false);
		add(btnInsert, "flowx,cell 0 6");

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setEnabled(false);
		add(btnUpdate, "flowx,cell 0 6");

		JButton btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		add(btnDelete, "flowx,cell 0 6");
	}

	private void tableSelectedRow(int row) {
		PricelistEntry entry = tableModel.getPricelistEntry(row);

		selectPricelistIndices(entry);

		cbEntry.setSelectedItem(entry.getEntry());
		cbExit.setSelectedItem(entry.getExit());
		cbCategory.setSelectedItem(entry.getCategory());
		cbCurrency.setSelectedItem(entry.getCurrency());
		spnPrice.setValue(entry.getPrice());
	}

	private void tableDeselectRow() {
		cbEntry.setSelectedItem(null);
		cbExit.setSelectedItem(null);
		cbCategory.setSelectedItem(null);
		cbCurrency.setSelectedItem(null);
		spnPrice.setValue(0);
	}

	private void selectPricelistIndices(PricelistEntry entry) {
		List<Pricelist> found = ctx.getPricelistService().getContaining(entry);
		int indices[] = new int[found.size()];
		for (int i = 0; i < found.size(); ++i) {
			indices[i] = found.get(i).getId();
		}
		lstPricelist.setSelectedIndices(indices);
	}

	@Override
	public void onShow() {
		tableModel.fireTableDataChanged();
	}
}
