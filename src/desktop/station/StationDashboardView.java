package desktop.station;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import core.station.IStationService;
import core.station.Station;
import core.station.location.ILocationService;
import core.station.location.Location;

import javax.swing.JLabel;
import javax.swing.JComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class StationDashboardView extends JPanel {
	public StationDashboardView(IStationService service, ILocationService locationService) {
		setLayout(new MigLayout("", "[grow]", "[grow][][][][]"));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "grow,flowy,cell 0 0");
		
		tableModel = new StationTableModel(service);
		table = new JTable(tableModel);
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
		
		location = new JComboBox<Location>(locationService.getAll().toArray(new Location[0]));
		add(location, "cell 0 3,growx");
		
		JButton btnNewButton_1 = new JButton("Insert");
		add(btnNewButton_1, "flowx,cell 0 4");
		
		update = new JButton("Update");
		update.setEnabled(false);
		add(update, "cell 0 4");
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update(table.getSelectedRow());
			}
		});
		
		JButton btnNewButton_3 = new JButton("Delete");
		add(btnNewButton_3, "cell 0 4");
	}
	
	private void update(int row) {
		Station station = tableModel.getStation(row);
		station.setCode(code.getText());
		station.setType((Station.Type)type.getSelectedItem());
		station.setLocation((Location)location.getSelectedItem());
		tableModel.fireTableRowsUpdated(row, row);
	}
	
	private static final long serialVersionUID = 4488314020615833614L;
	private StationTableModel tableModel;
	private JTable table;
	private JTextField code;
	private JComboBox<Station.Type> type;
	private JComboBox<Location> location;
	private JButton update;

	private void tableSelectedRow(int row) {
		code.setText((String) table.getValueAt(row, 1));
		type.setSelectedItem((Station.Type) table.getValueAt(row, 2));
		location.setSelectedItem(tableModel.getLocation(row));
		
		code.setEnabled(true);
		type.setEnabled(true);
		location.setEnabled(true);
		update.setEnabled(true);
	}
	
	private void tableDeselectRow() {
		code.setText("");
		type.setSelectedItem(null);
		location.setSelectedItem(null);
		
		code.setEnabled(false);
		type.setEnabled(false);
		location.setEnabled(false);
		update.setEnabled(false);
	}
}
