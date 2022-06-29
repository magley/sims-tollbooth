package desktop.pricelist;

import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
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

		JButton btnInsert = new JButton("Insert");
		btnInsert.setEnabled(false);
		add(btnInsert, "flowx,cell 0 2");

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setEnabled(false);
		add(btnUpdate, "flowx,cell 0 2");

		JButton btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		add(btnDelete, "flowx,cell 0 2");
	}

	private void tableSelectedRow(int row) {
		Pricelist p = tableModel.getPricelist(row);
		String datetime = p.getStart().toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " "
				+ p.getStart().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
		txtStart.setText(datetime);
	}

	private void tableDeselectRow() {
		txtStart.setText("");
	}

	@Override
	public void onShow() {
		tableModel.fireTableDataChanged();
	}

}
