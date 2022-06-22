package desktop.station;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.station.IStationService;
import core.station.Station;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class StationDashboardView extends JPanel {
	public StationDashboardView(IStationService service) {
		setLayout(new MigLayout("", "[grow]", "[grow][][][][]"));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "grow,flowy,cell 0 0");
		
		table = new JTable(new StationTableModel(service));
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	if (table.getSelectedRow() >= 0 && table.getSelectedRowCount() == 1)
	        		tableSelectedRow(table.getSelectedRow());
	        	else
	        		tableDeselectRow();
	        }
	    });
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("Code:");
		add(lblNewLabel, "flowx,cell 0 1");
		
		code = new JTextField();
		add(code, "cell 0 1,growx");
		code.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Type:");
		add(lblNewLabel_1, "flowx,cell 0 2");
		
		type = new JComboBox<Station.Type>(Station.Type.values());
		add(type, "cell 0 2,growx");
		
		JLabel lblNewLabel_2 = new JLabel("Location:");
		add(lblNewLabel_2, "flowx,cell 0 3");
		
		location = new JLabel("Select location...");
		add(location, "cell 0 3,growx");
		
		JButton btnNewButton = new JButton("Select location");
		add(btnNewButton, "cell 0 3");
		
		JButton btnNewButton_1 = new JButton("Insert");
		add(btnNewButton_1, "flowx,cell 0 4");
		
		JButton btnNewButton_2 = new JButton("Update");
		add(btnNewButton_2, "cell 0 4");
		
		JButton btnNewButton_3 = new JButton("Delete");
		add(btnNewButton_3, "cell 0 4");
	}
	private static final long serialVersionUID = 4488314020615833614L;
	private JTable table;
	private JTextField code;
	private JComboBox<Station.Type> type;
	private JLabel location;

	private void tableSelectedRow(int row) {
		code.setText((String) table.getValueAt(row, 1));
		type.setSelectedItem((Station.Type) table.getValueAt(row, 2));
		location.setText((String) table.getValueAt(row, 3));
	}
	
	private void tableDeselectRow() {
		code.setText("");
		type.setSelectedIndex(0);
		location.setText("Select location...");
	}
}
