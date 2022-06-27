package desktop.pricelist;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.AppContext;
import core.pricelist.Pricelist;
import desktop.ITabbedPanel;
import net.miginfocom.swing.MigLayout;

public class PricelistDashboardView extends JPanel implements ITabbedPanel {

	private static final long serialVersionUID = 8038930163054264768L;

	private AppContext ctx;

	private PricelistTableModel tableModel;
	private JTable table;
	private JTextField txtStart;
	private JComboBox<Pricelist.Active> cbActive;

	public PricelistDashboardView(AppContext ctx) {
		this.ctx = ctx;
		setLayout(new MigLayout("", "[grow]", "[grow][][][]"));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "grow,flowy,cell 0 0");

		tableModel = new PricelistTableModel(ctx.getPricelistService());
		table = new JTable(tableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (table.getSelectedRow() >= 0 && table.getSelectedRowCount() == 1)
					tableSelectedRow(table.getSelectedRow());
				else
					tableDeselectRow();
			}
		});
		scrollPane.setViewportView(table);

		JLabel lblStart = new JLabel("Start");
		add(lblStart, "flowx, cell 0 1");

		txtStart = new JTextField();
		add(txtStart, "cell 0 1,growx");
		
		JLabel lblActive = new JLabel("Active");
		add(lblActive, "flowx, cell 0 2");

		cbActive = new JComboBox<Pricelist.Active>(Pricelist.Active.values());
		add(cbActive, "cell 0 2,growx");

		JButton btnInsert = new JButton("Insert");
		btnInsert.setEnabled(false);
		add(btnInsert, "flowx,cell 0 3");

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setEnabled(false);
		add(btnUpdate, "flowx,cell 0 3");

		JButton btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		add(btnDelete, "flowx,cell 0 3");
	}

	private void tableSelectedRow(int row) {
		Pricelist p = tableModel.getPricelist(row);
		txtStart.setText(p.getStart().toString());
		cbActive.setSelectedItem(p.getActive());
	}

	private void tableDeselectRow() {
		txtStart.setText("");
		cbActive.setSelectedItem(null);
	}

	@Override
	public void onShow() {
		tableModel.fireTableDataChanged();
	}

}
